Node Set Java parsing example
=============================

This is an excerpt from Spire – Spotify Internal Remote Execution, a server
orchestration tool. It serves as an example of doing text parsing of a small
language in Java. For completeness, it also includes a small driver program
to test the language.

Source organization
-------------------
This is a standard Maven project. Compiling should be as easy as

> mvn compile

or

> ./nodeset

The `nodeset` script runs compilation if `target/classes` is missing, and
will then run the `NodeSetMain` class. (Yes, this was developed on a *nix
system!)

The CUP grammar file is in `src/main/cup`, and the scanner is in
`src/main/jflex`. Some glue classes are in the `java` subdirectory.

Language
--------
The language is a simple set-based language. It supports
[the four basic operators](http://en.wikipedia.org/wiki/Set_theory#Basic_concepts_and_notation),
set literals, singleton sets and function calls.

### Basic operators

* `a | b` or `a ∪ b`
* `a & b` or `a ∩ b`
* `a \ b` or `a / b`
* `a ^ b` or `a ⊖ b` or `a ∆ b`

### Set literals

* `∅`
* `[ "a", "b", "c" ]`

### Singleton sets

* `@a.b.c`

is equivalent to

* `[ "a.b.c" ]`

and is convenient when working with hostnames.

### Function calls

* `a or a()`
* `a("b", 42, 4711, @a.b.c)`

Examples
--------
As this usually works with Spotify's internal host databases, this contains an
example function call handler called `FileSystemContext`. This implements two
functions:

* `fs.ls("path")` to return a set of file names, and
* `fs.ls.current` equivalent to `fs.ls(".")`, but showing a function call
  without parentheses.

This allows you to run the `nodeset` program with expressions like

* `nodeset ∅`
* `nodeset @example.com`
* `nodeset '["example.com"]'`
* `nodeset 'fs.ls("/") & fs.ls("/mnt")'`

These examples are executed if you run `./nodeset` without arguments.

Caveats
-------
Error reporting is very crude, and there is essentially nothing aside from CUP's
standard stuff here.

Authors
-------
Tommie Gannert
