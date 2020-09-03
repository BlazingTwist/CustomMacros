# Using the .conf Files for writing custom scripts

The .conf Files are HOCON files.  
You don't have to be familiar with HOCON to use this tool, but a rough understanding certainly will help.  
You can find an exhaustive documentation on HOCON [here](https://github.com/lightbend/config/blob/master/HOCON.md).

## General Structure

You can place any kind of HOCON valid text in the root of your .conf Files.  
The only section that requires strict attention (and is actually used outside of substitutions) is the `actions` object.  
In code, it is stored as a Map<ModuleName, Map<ActionName, List<Instruction>>>  
As such, it represents a hierarchy of ModuleName -> ActionName -> Instructions.

ModuleName and ActionName are up to you to decide (no duplicates), however Instructions are defined rigidly.

## Supported Instructions and Syntax

Every Instruction follows the same basic Syntax:
```
{
  type = InstructionType (name of Instruction)
  param1 = value1
  ...
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

#### ***ProbeScreen***

The ProbeScreen Instruction will compare a screenshot against every Pixel in the provided `pixelStateCollection`  
If all ProbingPixels within the PixelStateCollection are satisfied, it will execute the Action specified in `actionNameIfMatch`, otherwise `actionNameElse` (equivalent to executing a ChangeToAction Instruction)  
Note: PixelStateCollections and ProbingPixels are documented under [DataStructures used by Instructions](#datastructures-used-by-instructions).

```hocon
{
  type = probeScreen
  pixelStateCollection = [probingPixel1, ..., probingPixelN]
  actionNameIfMatch = ActionNameA
  actionNameElse = ActionNameB
}
``` 

<br/>

#### ***Repeat***

This Instruction will repeat another Action any given amount of times.  
The amount of repetitions is specified in the `count` field.  
A List of Instructions to execute is specified in the `action` field.  
Note: Unlike the ChangeToAction Instruction, this instruction will resume execution within the active Action upon completion.

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

```hocon
{
  type = tapKey
  keyEvent = VK_ENTER
  duration = [0-9]+
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