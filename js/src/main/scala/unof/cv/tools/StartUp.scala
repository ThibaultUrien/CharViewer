package unof.cv.tools

import scala.scalajs.js
import scala.scalajs.js.Any.jsArrayOps
import scala.scalajs.js.Any.wrapArray
import scala.scalajs.js.Dynamic
import scala.scalajs.js.JSApp

import unof.cv.base.CharLibBuilder
import unof.cv.base.DrawingContext
import unof.cv.base.JsBodyPart

object StartUp extends JSApp {
  def main():Unit = {
    val settings = Dynamic.global.charViewerSettings.asInstanceOf[CvSetting]
    
    val (cookieParam,cookieDate,fileParam,fileDate) = getCmParam
   
    def getOrElse[A](f: () => A, orElse: A) = {
      try {
        val v = f()
        if(js.isUndefined(v))//Result are randoms...
          orElse
        else{
          val a = 0
          //Good practice!!! Force it to throw if v is actually undefined.
          val b = if(v.toString().isEmpty()) a/0 
           v
        }
          
      } catch {
        case _: Throwable => orElse
      }
    }
    def loadCookie = init(
          cookieParam.bodyParts,
          getOrElse(() => cookieParam.colors,Nil),
          getOrElse(() => cookieParam.sliders.map(_.intValue()), Nil),
          getOrElse(() => cookieParam.choices.map(_.intValue()), Nil),
          getOrElse(() => cookieParam.selected.map(_.intValue()), Nil),
          settings
    )
    def loadFile = init(
          fileParam.bodyParts,
          getOrElse(() => fileParam.colors,Nil),
          getOrElse(() => fileParam.sliders.map(_.intValue()), Nil),
          getOrElse(() => fileParam.choices.map(_.intValue()), Nil),
          getOrElse(() => fileParam.selected.map(_.intValue()), Nil),
          settings
    )
    def loadNothing = init(
      js.Array(), 
      Nil,
      Seq(),
      Seq(),
      Seq(-1,-1,-1),
      settings
    )
    if(cookieDate > fileDate) {
      try {
        println("The cookie save is the most recent so I load it.")
        loadCookie
      }catch {
        case t :Throwable =>
          println("The cookie save was the most recent but something go wrong:")
          t.printStackTrace()
          println("I use the file save instead.")
          
          try{
            loadFile
          }catch {
            case t : Throwable =>
            println("There were also problem with the file save :-(.")
            t.printStackTrace()
            println("I start the application with an empty save.")
            loadNothing
          }
      }
    }else{
       try {
         println("The file save is the most recent so I load it.")
        loadFile
      }catch {
        case t :Throwable =>
          println("The file save was the most recent but something go wrong:")
          t.printStackTrace()
          println("I use the cookie save instead.")
          try{
            loadCookie
          }catch {
            case t : Throwable =>
            println("There were also problem with the cookie save :-(.")
            t.printStackTrace()
            println("I start the application with an empty save.")
            loadNothing
          }
      }
    }
    
  }
  def init(
      bodyParts : js.Array[JsBodyPart],
      colors : Seq[String],
      sliders : Seq[Int],
      choices : Seq[Int],
      selected : Seq[Int],
      settings : CvSetting
  ) = {
   
    val charContext = new DrawingContext(settings.characterCanvas)
    val charMaker = CharLibBuilder(bodyParts,settings.verbose)
    
    
    new CallbackCenter(choices,colors,sliders,selected,charContext,charMaker,settings)
  }
  private def getCmParam = {
    val fileParam = Dynamic.global.fileCMParams.asInstanceOf[CVParams]
    val cookieParam = Dynamic.global.cookieCMParams.asInstanceOf[CVParams]
    
    val fileDate = try{
      if(js.isUndefined(fileParam)){
        println("no files found")
        0l
      }else
        fileParam.date.longValue()
    }catch {
      case _ : Throwable => 
        println("no files found")
        0l
    }
    val cookieDate = try {
      if(js.isUndefined(cookieParam)){
        println("no cookie found")
        0l
      }else {
        cookieParam.date.longValue()
      }
    }catch {
      case _ :Throwable => 
        println("no cookie found")
        0l
    }
    
    
    (cookieParam,cookieDate,fileParam,fileDate)
    
  }
  def main(args: Array[String]): Unit = {
    main
  }
}