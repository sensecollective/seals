<!--

   Copyright 2016 Daniel Urban

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

--->

# seals

[*seals*](https://github.com/durban/seals) is an experimental project
by [Daniel Urban](https://github.com/durban), which provides tools for
**s**chema **e**volution **a**nd **l**anguage-integrated **s**chemata.

By using it, you will be able to

- define schemata by creating ordinary Scala datatypes;
- check the compatibility of different versions of a schema
  at compile time, as well as runtime;
- automatically derive serializers and deserializers for your
  schema-datatypes (currently only [circe] encoders and decoders
  are implemented);
- and communicate between components using different (but compatible)
  versions of a schema.

Since it's a fairly new project, not all of these features are
implemented yet. Bugs are to be expected as well. Contributions
to improve the project, and reports about issues you encounter
are welcome.

[//]: # (TODO: link to issue tracker + CONTRIBUTION.md)

## Getting started

*seals* is currently available for Scala 2.11. At the moment there are
no published JARs. Until they are available, you can depend directly on
[this git repo](https://github.com/durban/seals.git) by putting the
following into your `build.sbt` (this will cause `sbt` to depend on
the `core` subproject on the `master` branch; all commits there are
signed by key `36A8 2002 483A 4CBF A5F8 DF6F 48B2 9573 BF19 7B13`):

```scala
dependsOn(ProjectRef(uri("git://github.com/durban/seals.git#master"), "core"))
```

By using `seals-core`, you can define a schema simply by creating an ADT:

```tut:silent
final case class User(id: Int, name: String)
```

In the next version of the schema, you may want to add a new field
(with a default value):

```tut:silent
final case class UserV2(id: Int, name: String, age: Int = 42)
```

Thanks to the default value, these two versions are compatible with
each other. We can assert this by using the `Compat` type class:

```tut
import io.sigs.seals.Compat
Compat[User, UserV2]
```

If they wouldn't be compatible, we would get a *compile time* error
(because there would be no `Compat` instance available). For example:

```tut:fail
final case class UserV3(id: Int, name: String, age: Int) // no default `age`
Compat[User, UserV3] // error: could not find implicit value for ...
```

For a more detailed introduction to the `Compat` type class,
see [this example](core/src/main/tut/Compat.md). If you are
interested in other features (like automatic derivation of
serializers, or runtime compatibility checking), at the moment
the best way is to look at the sources (and Scaladoc comments,
and laws/tests).

## Project structure

The subprojects are as follows:

- [`core`](core): essential type classes (required)
- [`circe`](circe): automatic derivation of [circe]
  encoders and decoders (optional)
- [`laws`](laws): definitions of laws for the type classes in `core` (incomplete, for testing)
- [`tests`](tests): unittests (don't depend on this)

## Dependencies

*seals* depends on the following projects:

- [shapeless](https://github.com/milessabin/shapeless) provides
  the macros and type classes to automatically derive schemata
  and other type class instances for ADTs.
- [Cats](https://github.com/typelevel/cats) provides general
  functional programming tools which complement the Scala standard library.
- [circe] provides the JSON framework for which `seals` derives the encoders and decoders.

For testing, it also uses:

- [ScalaTest](https://github.com/scalatest/scalatest) for the unittests,
- [ScalaCheck](https://github.com/rickynils/scalacheck) for automated
  property-based testing,
- and [scalacheck-shapeless](https://github.com/alexarchambault/scalacheck-shapeless)
  to generate pseudorandom ADT instances.

A compile time–only dependency is the [SI-2712-fix plugin](https://github.com/milessabin/si2712fix-plugin)
(although hopefully it will be replaced by the [Typelevel Scala compiler](https://github.com/typelevel/scala)
soon).

## License

*seals* is open source software under the [Apache License v2.0](https://www.apache.org/licenses/LICENSE-2.0).
For details, see the [LICENSE.txt](LICENSE.txt) and [NOTICE.txt](NOTICE.txt) files.

[circe]: https://github.com/travisbrown/circe
