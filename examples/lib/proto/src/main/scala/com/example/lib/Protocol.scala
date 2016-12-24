/*
 * Copyright 2016 Daniel Urban
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lib

object Protocol {

  object v1 {

    sealed trait Request extends Product with Serializable
    final case class Random(min: Int, max: Int) extends Request with v2.Request
    final case class Seed(value: Long) extends Request with v2.Request

    sealed trait Response extends Product with Serializable
    final case class RandInt(value: Int) extends Response with v2.Response
    final case object Seeded extends Response with v2.Response
  }

  object v2 {

    sealed trait Request extends Product with Serializable
    final case class RandomLong(min: Long, max: Long) extends Request

    sealed trait Response extends Product with Serializable
    final case class RandLong(value: Long) extends Response
  }
}