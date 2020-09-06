# Running scripts

Running scripts with CustomMacros is fortunately simple:  
You'll need the `custom-macros.jar` executable jar and your `.conf` scripts.  
You can run scripts using this command pattern: `java -jar {path-to-custom-macros.jar} {path-to-script-1} ... {path-to-script-n}`  
As your `path-to-script` you can specify a relative or absolute path a file or folder.  
If you supply a folder as your `path-to-script` CustomMacros will load *every* `.conf` file at any depth within that folder.  
You can also take a look at the `example` folder for a general reference.

<br/>

# Using the .conf Files for writing custom scripts

The .conf Files are HOCON files.  
You don't have to be familiar with HOCON to use this tool, but a rough understanding certainly will help.  
You can find an exhaustive documentation on HOCON [here](https://github.com/lightbend/config/blob/master/HOCON.md).

## General Structure

You can place any kind of HOCON valid text in the root of your .conf Files.  
There are however a few reserved object keys that require a strictly defined structure:

* actions
* displayConfig
* flowControl
* hotkeys
* keybinds
* onStuck

<br/>

#### ***actions***

In code, the actions object is stored as a Map<ModuleName, Map<ActionName, List<Instruction>>>  
As such, it represents a hierarchy of ModuleName -> ActionName -> Instructions.

ModuleName and ActionName are up to you to decide (no duplicates), however Instructions are rigidly defined.  
see [Supported Instructions and Syntax](#supported-instructions-and-syntax)

<br/>

#### ***displayConfig***

This contains information to streamline compatibility of scripts across different resolutions.  
`sourceResolution` contains the resolution that was used for creating the script.  
`targetResolution` contains the resolution the script is mapped to (typically the resolution of your screen).  
It is imperative that both resolutions are specified in the config, even if they have the same value.

```hocon
displayConfig {
  sourceResolution {width = 3840, height = 2160}
  targetResolution {width = 1920, height = 1080}
}
```

<br/>

#### ***flowControl***

In this object you can assign keybinds to "flowControl"-functions by assigning the desired keybindName to them.  
Currently, there are four flowControl-functions:  

`pauseAction` will pause execution of the active Action.  
`unpauseAction` will continue execution of the (paused) active Action.  
`quitModule` will force-quit the active Action and its Module.  
`exit` will force-quit the application.

```hocon
flowControl {
  pauseAction = keybindName1
  unpauseAction = null //not assigned
                       //quitModule is also not assigned
  exit = keybindName2
}
```

<br/>

#### ***hotkeys***

Here you can specify all the keybinds to entry-points for your actions.  
The keybind is specified as the object key and must correspond to the registered keybindNames  
Every hotkey object has 3 fields:    
`global` boolean value, global hotkeys can trigger during execution of another action and will act like the "ChangeToAction" Instruction.  
`module` the name of the module that this hotkey executes.  
`action` the name of the action (within the executed module) that this hotkey executes.

```hocon
hotkeys = {
  keybindName1 {
    global = false
    module = moduleName
    action = actionName1
  }
  //...
  keybindNameN {
    global = true
    module = moduleName
    action = actionNameN
  }
}
```

<br/>

#### ***keybinds***

In this object all available keybinds and their assigned keys are specified.  
The structure is a Map<String, List<KeyModule>>, as such, every key in the keybinds object is used as the name for the keybind.  
There are currently four types of KeyModules:  
`KeyDown` Is satisfied if the assigned key is pressed.  
`KeyUp` Is satisfied if the assigned key is released.  
`OnKeyDown` Is only satisfied if the key has just been pressed.  
`OnKeyUp` Is only satisfied if the key has just been released.
  
You can read up on the naming for `keyEvent` [here](https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html).

```hocon
keybinds {
  keybindName1 = [
    {type = keyDown, keyEvent = VK_CONTROL}
    {type = keyUp, keyEvent = VK_SHIFT}
    {type = onKeyDown, keyEvent = VK_P}
  ]
  //...
  keybindNameN = [
    {type = keyDown, keyEvent = VK_CONTROL}
    {type = keyDown, keyEvent = VK_SHIFT}
    {type = onKeyDown, keyEvent = VK_P}
  ]
}
```

<br/>

#### ***onStuck***

In this object you can specify a certain Action to execute if an Instruction gets stuck for a certain duration.  
There are currently 3 fields in the onStuck object:  
`delay` the amount of time an Instruction has to be stuck for to trigger this function.  
`module` the name of the module that is to be executed.  
`action` the name of the action that is to be executed.

If you don't want to specify any onStuck behaviour, you can omit this object from the script.

<br/>

## Supported Instructions and Syntax

Every Instruction follows the same basic Syntax:
```hocon
{
  type = InstructionType (name of Instruction)
  param1 = value1
  //...
  paramN = valueN
}
```

Every Instruction
* is marked as an object by enclosing curly brackets `{}`
* contains a field key `type` with a value that corresponds to an Instruction-name (case-insensitive)
* contains any amount of additional field keys that are required by the InstructionType

It is recommended to use the short-form for Instructions to improve readability (as scripts otherwise tend to become very long)

```
{type = InstructionType, param1 = value1, ..., paramN = valueN}
```

<br/>

#### ***ChangeToAction***

When this Instruction is executed, the current Action will be cancelled, and the Action specified in the `actionName` field will execute instead.  
Note: `actionName` must correspond to an ActionName within the active Module, otherwise an Exception is thrown

```hocon
{
  type = changeToAction
  actionName = ActionName
}
```

<br/>

#### ***ExecCommand***

You can use this Instruction to run command line commands.  

```hocon
{
  type = execCommand
  command = shutdown /h
}
```

<br/>

#### ***LogMessage***

You can use this Instruction to log a `message` to the console.  
Primary purpose is for debugging your scripts.

```hocon
{
  type = logMessage
  message = This is some kind of debug-text
}
```

<br/>

#### ***MouseClick***

Use this Instruction to press and release mouse buttons.  
`duration` specifies the duration the button is held down.  
`mouseButton` is any number starting from 1 (LMB)  
Note: this Instruction will halt program execution for the given duration.

```hocon
{
  type = mouseClick
  mouseButton = [1-9]
  duration = [0-9]+
}
```

<br/>

#### ***MouseMove***

This Instruction will try to smoothly move your mouse from the start position to the end position within the specified duration.  
Note: this Instruction will halt program execution for the given duration.

```hocon
{
  type = "mouseMove"
  startX = [0-9]+
  startY = [0-9]+
  endX = [0-9]+
  endY = [0-9]+
  duration = [0-9]+
}
```

<br/>

#### ***MousePress***

This Instruction will press and hold the specified mouse key until a `MouseRelease` Instruction is executed.  
`mouseButton` is any number starting from 1 (LMB)

```hocon
{
  type = mousePress
  mouseButton = [1-9]
}
```

<br/>

#### ***MouseRelease***

This Instruction will release the specified mouse key until (provided the button is pressed, see `MousePress`)    
`mouseButton` is any number starting from 1 (LMB)

```hocon
{
  type = mouseRelease
  mouseButton = [1-9]
}
```

<br/>

#### ***MouseSnap***

This Instruction will snap the mouse to the specified position.  
Note: This (for some reason) tends to be a rather inaccurate operation.  
If you can spare the milliseconds, `MouseMove` is more precise.

```hocon
{
  type = mouseSnap
  x = [0-9]+
  y = [0-9]+
}
```

<br/>

#### ***PressKey***

This Instruction will press and hold the specified key upon execution.  
Make sure to call ReleaseKey eventually, or consider using TapKey instead.  
You can read up on the naming for `keyEvent` [here](https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html).

```hocon
{
  type = pressKey
  keyEvent = VK_ENTER
}
```

<br/>

#### ***ProbeScreen***

The ProbeScreen Instruction will compare a screenshot against every Pixel in the provided `pixelStateCollection`  
If all ProbingPixels within the PixelStateCollection are satisfied, it will execute the Action specified in `actionNameIfMatch`, otherwise `actionNameElse` (equivalent to executing a ChangeToAction Instruction)  
If you want to conditionally continue the execution of the current action, you can specify `null` as `actionNameIfMatch` or `actionNameElse`  
Note: PixelStateCollections and ProbingPixels are documented under [DataStructures used by Instructions](#datastructures-used-by-instructions).

```hocon
{
  type = probeScreen
  pixelStateCollection = [probingPixel1, ..., probingPixelN]
  actionNameIfMatch = ActionName
  actionNameElse = null
}
``` 

<br/>

#### ***ReleaseKey***

This Instruction will release specified key upon execution. (provided that the key is pressed)  
Make sure to call PressKey first, or consider using TapKey instead.  
You can read up on the naming for `keyEvent` [here](https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html).

```hocon
{
  type = releaseKey
  keyEvent = VK_ENTER
}
```

<br/>

#### ***Repeat***

This Instruction will repeat another Action any given amount of times.  
The amount of repetitions is specified in the `count` field.  
A List of Instructions to execute is specified in the `action` field.  
Note: Unlike the ChangeToAction Instruction, this Instruction will resume execution within the active Action upon completion.

```hocon
{
  type = repeat
  count = [0-9]+
  action = [Instruction1, ..., InstructionN]
}
```

<br/>

#### ***RunAction***

The purpose of this Instruction is to enable easy usage of HOCON substitution, it is functionally identical to a Repeat Instruction with `count = 1`

```hocon
{
  type = runAction
  action = [Instruction1, ..., InstructionN]
}
```

<br/>

#### ***TapKey***

Presses and holds a key for a given `duration` (in milliseconds).  
You can read up on the naming for `keyEvent` [here](https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html).  
Note: The tapKey Instruction will halt program execution for the given duration.

```hocon
{
  type = tapKey
  keyEvent = VK_ENTER
  duration = [0-9]+
}
```

<br/>

#### ***TestKeybind***

This Instruction will check whether the specified keybind is pressed or not.  
If it is pressed, the action in `actionNameIfPressed` is executed, otherwise `actionNameElse`  
You can specify the value `null` as an ActionName to conditionally continue the execution of the active action.

```hocon
{
  type = testKeybind
  keybind = keybindName
  actionNameIfPressed = ActionName
  actionNameElse = null
}
```

<br/>

#### ***Wait***

As the naming implies, will halt the execution for a given `duration` (in milliseconds).

```hocon
{
  type = wait
  duration = [0-9]+
}
```

<br/>

#### ***WaitForKeybind***

This instruction will halt program execution until the specified keybind is pressed.

```hocon
{
  type = waitForKeybind
  keybind = keybindName
}
```

<br/>

#### ***WaitForPixelState***

This Instruction will continuously take screenshots until all ProbingPixels in its `pixelStateCollection` are satisfied.  
Note: PixelStateCollections and ProbingPixels are documented under [DataStructures used by Instructions](#datastructures-used-by-instructions).

```hocon
{
  type = waitForPixelState
  pixelStateCollection = [probingPixel1, ..., probingPixelN]
}
```

<br/>

## DataStructures used by Instructions

#### ***PixelStateCollections and ProbingPixels***

Within .conf Files, `PixelStateCollection` is effectively just a List of ProbingPixels, their naming carried over from the code implementation.  
`ProbingPixels` are objects that define rules, which pixels on the screen have to match in order to be satisfied.  
Like Instructions, ProbingPixels have a `type` field, which corresponds to a ProbingPixel-name (case-insensitive)
  
Currently, there are 4 types of supported ProbingPixels:
* ExactPixelMatch  
    This ProbingPixel requires the OnScreenPixel to have *exactly* the same color.
```hocon
    {
      type = ExactPixelMatch
      x = [0-9]*
      y = [0-9]*
      rgb = colorObject
    }
```

<br/>

* ExactPixelFilter  
    This ProbingPixel requires the OnScreenPixel to have any color *except* the specified color.
```hocon
    {
      type = ExactPixelFilter
      x = [0-9]*
      y = [0-9]*
      rgb = colorObject
    }
```

<br/>

* RangedPixelMatch  
    This ProbingPixel requires that the red, green and blue component of the OnScreenPixel each are within or equal to the upper and lower bounds specified.  
    `rgbFrom` specifies the lower bound, `rgbTo` the upper bound.
```hocon
    {
      type = RangedPixelMatch
      x = [0-9]*
      y = [0-9]*
      rgbFrom = colorObject
      rgbTo = colorObject2
    }
```

<br/>

* RangedPixelFilter  
    This ProbingPixel requires that the red, green and blue component of the OnScreenPixel each are outside of the upper and lower bounds specified.  
    `rgbFrom` specifies the lower bound, `rgbTo` the upper bound.
```hocon
    {
      type = RangedPixelFilter
      x = [0-9]*
      y = [0-9]*
      rgbFrom = colorObject
      rgbTo = colorObject2
    }
```

<br/>

#### ***Color***

Simply an Object containing `red`, `green` and `blue` fields.  
Their values range from `0` to `255`.

```hocon
{
  red = [0-9]+
  green = [0-9]+
  blue = [0-9]+
}
```