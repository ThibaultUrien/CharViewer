#CharViewer

This project aim to provide a free tool for all the developers that want to integrate a character viewer in their games. 
This project is mostly written in Scala, but the source file are then compiled in JavaScript. That mean you (normally) can use this utility for flash, html5 and Unity games are they all perfectly handle JavaScript.


This project doesn't provide any (interesting) assets. You have to bring your owns. What it does is helping you to provide you a convenient graphical interface to wrap things up.

This project is divided in two distinct parts: 

1. A suits of tools that help you to preview your work and help you to setup things. It work by itself, you just have to open tools.html in a web browser to use it. 
2. A library that can be used form JavaScript to display the result of your work in any application that can use this language.

##How things works.

This character viewer use three levels to manage it's assets. You need to understand what they are to use it.

###The Categories.
The category level is the top level of this character viewer. The categories are represented by the colorful tabs you can see at the top of the tool window (in tool.html). Categories are used to regroup the different option you have to display a same thing. For example if your character can have different hairstyles, you want to create a category named hairstyles with all the different hairstyles he can have. 
The possible option in categories are called Parts. Only one Part that is displayed per categories just as you can only have one hairstyle at once.

###The Parts.
The parts represent the possible options you can display in one category. To see make a part visible put your mouse over the category that contain it and then click on its name.
Each Part also own a translation, a rotation, a scale and a Z value. The Z value is used to determine what is drawn atop of what with the element with the highest Z drawn the more in front. Each part also contain a collection of Images. The Images of a part is what is drawn on the screen when you select a Part. For example if in your Category “Hairstyle” you have a Part “Long hair with bangs” you will have an image for the bangs that must be drawn in front of you character head and an other for the hair that must be drawn bellow it. that are applied to each of their Images. 

###The Images.
The Images are what is actually drawn on you screen. Each image is linked with an image file stored in the folder “images” or in a sub-folder of its. Each image is also linked with one color variable. When displayed the Image will be first multiplied by the value of this color variable. Many Image can be linked with the same color variable. Linking an Image with the color variable “None” make the image bound to none of it. In this case the image will always be displayed with its original colors.
Each image also have a display condition. The display condition allow you to hide an image even if the part that contain it is visible. That can be helpful to prevent the char viewer to display meaningless things. That can also be used to make a part automatically adapt to an other. For example, if your character can be more or less fit, you want it to be visible even if clothed. So if your character is average, you show the default image of his cloth, but you will set the display conditions of its cloth so that if he get a pot belly, this version of his clothes will be hidden and the version where the fabric is stretched at his midsection shown.

Currently here are the implemented display conditions:

* Always: The image is visible is the part that contain it is selected. It's just as not setting any condition.
* Linked to: The image is visible if an other part is also visible. In the example we could have the Image using "green_shirt_fat.png" of the Part "Green shirt" linked to the body part named "Pot belly".
* No visible links: The image is visible if no Linked to image is visible. It can be used to make an image only visible by default. In our example, we could have the Image using "green_shirt_average.png" of the Part "Green shirt" set to this condition so that if it's visible only if we aren't already using a more adapted shirt shape.

As the Parts, each image have a translation, a rotation, a scale and a Z value. To draw an image the viewer will apply it its own transformation but also the transformation of the Part that contain it. For example you have your hairstyle named “Long hair with bangs” the first time you add this Part to the category “Hairstyle” you release that with the default parameters the bangs didn't connect well with the rest of the hair, there is a big unwanted gap that will make your character look like a man in his fifty’s that start loosing its hair. So first you move the Image (by changing its translation or by dragging it) of the bangs so that they connect well with the rest of the hair. But there is an other problem: the hairstyle look good but is drawn in the top left corner of your screen instead of the top of the head of your character. Now you have two choice: move each Image of composing your hairstyle, or directly move the Part “Long hair with bangs” that contain all your Image if you move the Part, all the image it contain will be moved as one solid object.



## The tools interface


### The View

The big area at the center is where the character is displayed. If you use Firefox you can also click on the elements you see to open their Parameter menu. You can also drag the elements on the view using your mouse. Note that if you have the Parameter Menu of a part open and you drag on of its image, you will move tho whole Part as one object.


### The Selection Menu

At the top of the View you can see the names of your Categories, if you have any, in colored tabs. A bit on the left there a red tab with a + in it. Click on it to create a new Category. You can put you mouse over a tab to make the Parts a Category contain and select the one that you want to see. You can also click on the name of the Category to edit its parameters.
Each Category's tab contain a list of the Part it contain. The part that currently drawn is marked in yellow. If you click again on the visible Part, you will open its Parameter menu. Once a Part's Parameter is open, you can drag the whole Part by any of it's image visible on the View.


### The Parameter Menu

You can find it on your right. There is three modes for this menu depending it control an Image, a Part of a whole Category. Yet all those modes have strong similarities.
The functionality of the Parameter Menu are organized by panels.
#### The location panel
Always visible, allow you to send the Image, Part or Categories somewhere else with all their content. You can provide name of Part or Categories that already exist, which will result in the fusion of the two. You can also provide name of things that don't exist to create new Part or Categories or rename them.
#### The source panel
Only visible for Images. Allow you to change the file used by this image  without loosing the other parameters it have. Set it to “None” to have an invisible image. If a folder named “images” is contained in the path of the file you add, it will be assumed as the images folder of the character viewer and it will be removed from the inputted path with everything that come before. The source panel don't use a file picker you have to write the path to the image by yourself. Yet there is a suggestion system that will do its best to help you. The reason is file picker can always provide the content of a file and not its location. So if I had used a file picker, either you would have to pick by hand all your image each time you start the application because I don't know were to look for them, or you will have to update them by hand each time you change them: I've stored all the image data in a place I know but I can't guess when those data are outdated and I can't automatically have a look a the original files as I still don't know where they are stored.
_Note that if you have many image to import its better to use UpdateViewerParams.jar_
#### The import panel
Visible for the Categories and the Parts but don't produce the same results for those two. The import panel take as input the path to one or many image file. When clicking on import if the Parameter menu is open on a Categories, this category will receive one new Part per image file. Each of those new Parts have exactly one Image to display its image file. If the Parameter Menu is open on a Part, the said Part will receive one new Image for each files. If you add many file write each of them between path.
_Note that if you have many Parts or Categories to add its better to use UpdateViewerParams.jar_
#### The components panel
Visible for Categories and Parts. You can see of what they are made and click on the names to open the Parameter Menu of what compose them. Very useful if you have pushed and image out of the View.
#### The rotation panel
Always visible. The unit is °. Things rotate around their top left corner. For Parts and Images, it set their own rotation value. For Categories, as they doesn't own a rotation value, it will override the rotation value of all of the owned Parts (only if modified).
#### The scale panel
Always visible.  For Parts and Images, it set their own scales value. For Categories, as they doesn't own scale values, it will override the scale values of all of the owned Parts (only if modified).
#### The translation panel
Always visible. The position in pixel of the top left corner of the component with the top left corner of the View as origin, x toward left and y downward. For Parts and Images, it set their own translation values. For Categories, as they doesn't own translation values, it will override the translation values of all of the owned Parts (only if modified).
#### The Z panel
Always visible. Used to determine the order in which the Image will be drawn. The higher the Z the latter the image will be drawn. For Parts and Images, it set their own Z value. For Categories, as they doesn't own a Z value, it will override the Z value of all of the owned Parts (only if modified).
#### The color panel
Always visible. For Images _only_ it control that color variable that's bound with the image. Set it to “None” if you don't want this Images bound with any. For _both Categories and Parts_, if changed, bind the all the contained Images to the same color.
If the given name doesn't match with any existing color variable, a new color variable is created. Remember to click on the "Bind" button to make your choice effective.
#### The display condition panel
Visible only for Images. Alow you to decide when the controlled image should be displayed and when it shouldn't. Remember to click on the "Set condition" button to make your choice effective.
#### The copy and delete buttons
Copy/delete the controlled element with all its content.


### The Color Panel

You can open and close it by clicking on the pallet at the bottom left. It allow you to change the value of the color variable. Adding and deleting color variable is automatically done when binding and unbinding Image to them (via the Parameter Menu). All color variable shown in the Color Panel are at least bound to one Image.


### The Save Button

Generate a new cvParams.js containing (almost certainly) the exact state of the tool at the moment when you clicked. Once generated, your browser will offer you to download the new file. Currently, the new file will receive a .txt at the end of its name. Remove it or the file will be just ignored. If replace the old cvParams.js by this new one and  restart the tool.html later you will find it just as you saved it (provided that you didn't moved, renamed or deleted any useful image during this period of time.
Moreover, the save button is also an export button for the integrated char viewer as cvParams.js is also the file used by the library. 



###Importing many images

This procedure is the one  recommended to create the needed Categories, Parts and Images.
If you have many image to import at once. Use the UpdateViewerParams.jar program that's located in the char viewer file. You need to have Java installed on your computer for this to work. This small program will explore the images folder and update cvParams.js to create all the Categories, Part and Images it think are needed.
UpdateViewerParams is an updater, that mean if you already have saved things in cvParams.js, it probably won't be deleted. Deletion will occur in the following situation: an Image that is linked to a file that doesn't exist anymore, a Part that is empty of any Image or a Category that is empty of any Parts.

When exploring the image folder, UpdateViewerParams will do as following: for each folder discovered in the images folder, it will create a new Category with the same name if it doesn't already exist. Files that aren't folder will be ignored. 
Then for each file in one of the discovered folder, if its an other folder, it will add a new Part to the Category named from the parent of this folder. If its not, it will still create a new Part but containing only one Image linked to the discovered file.
At least it will do a last exploration in each folder that where identified as a Part and for each file it contain, it will add a new Image linked to the discovered file to the Part named after this same folder.


## Using the Character Viewer in an application

//TODO
 CharViewer(bodyParts : js.Array[JsBodyPart], val targetCanvas : String, val imageHome : String)
 drawChar()
 choose(category : String, part : String)
 chooseAll(dic : js.Dictionary[String])
 chooseColor(variableName : String, choosenColor : String)
 chooseAllColors(dic : js.Dictionary[String])

