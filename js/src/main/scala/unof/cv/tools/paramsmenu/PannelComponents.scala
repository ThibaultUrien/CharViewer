package unof.cv.tools.paramsmenu

import unof.cv.base.charmaker.CMPart
import org.scalajs.jquery.jQuery
import org.scalajs.jquery.JQuery
import org.scalajs.jquery.JQueryEventObject
import unof.cv.base.charmaker.CMAdress
import unof.cv.base.charmaker.LayersSelector
import unof.cv.base.charmaker.SelectImages
import scala.scalajs.js
import scala.scalajs.js.Any.fromFunction1
import scala.scalajs.js.Any.fromInt
import scala.scalajs.js.Any.fromString
import unof.cv.tools.CallbackCenter
import unof.cv.tools.CvSetting
import unof.cv.base.charmaker.SelectShapes

object PannelComponents {
   def refresh(callbacks : CallbackCenter, settings : CvSetting ){
    val CMAdress(category,part,image,select) = callbacks.selection
    val options = callbacks.currentOptions
    
    val componentsPannel = jQuery(settings.elementComponentDiv)
    
    if (image < 0) {
        componentsPannel.show(500)
        val partList = jQuery(settings.partMenuLayerList)
        partList.empty()

        def layersOfAPart(selectedPart: CMPart, dl: JQuery, pIndex: Int = -1) = {
          def printImg(nameindex: (String,Int),layerSelect : LayersSelector) = {
            val dd = jQuery("<dd>")
            val (name,index) = nameindex
            val imgName = "<ins>" + name + "</ins>"
            dd.append(imgName)
            if (pIndex < 0)
              dd.click(setSelectedImage(callbacks, index,layerSelect) _)
            else
              dd.click(setSelectedImage(callbacks, pIndex, index,layerSelect) _)
            dl.append(dd)
          }
          selectedPart.images.map(_.ref).zipWithIndex.foreach( printImg(_,SelectImages))
          selectedPart.shapes.indices.foreach{
            i=> printImg(("Shape "+(i+1)+" of "+selectedPart.partName,i),SelectShapes)
          }
        }

        if (part < 0) {
          options.categories(category).possibleParts.zipWithIndex.foreach {
            case (part, index) =>
              val dl = jQuery("<dl>")
              val dt = jQuery("<dt>")
              dt.append("<b>" + part.partName + " : </b>")
              dt.click(setSelectedPart(callbacks, index)_)
              dl.append(dt)
              layersOfAPart(part, dl, index)
              partList.append(dl)
          }
        } else {
          val selectedPart = options.categories(category).possibleParts(part)
          val dl = jQuery("<dl>")
          val dt = jQuery("<dt>")
          dt.append("<b>Layers : </b>")
          dl.append(dt)
          layersOfAPart(selectedPart, dl)
          partList.append(dl)
        }
      } else {
        componentsPannel.hide(500)
      }
  }
  
  def bind(callbacks : CallbackCenter, settings : CvSetting ){
  }
   private def setSelectedImage(callback: CallbackCenter, partIndex: Int, imgIndex: Int,select : LayersSelector)(evt: JQueryEventObject) = {
    callback.onLayerSelected(partIndex, imgIndex,select)
  }
  private def setSelectedImage(callback: CallbackCenter, imgIndex: Int,select : LayersSelector)(evt: JQueryEventObject) = {
    callback.onLayerSelected(imgIndex,select)
  }
  private def setSelectedPart(callback: CallbackCenter, partIndex: Int)(evt: JQueryEventObject) = {
    callback.onPartSelected(partIndex)
  }
}