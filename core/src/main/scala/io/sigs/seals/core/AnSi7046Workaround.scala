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

package io.sigs.seals
package core

/**
 * This is an ugly workaround for SI-7046,
 * regarding `Model` and 2 of its subclasses.
 */
private final class AnSi7046Workaround {

  private def `This is to force Composite`: Model.Composite[_, _] =
    sys.error("This should never be called")

  private def `And this is to force Vector`: Model.Vector =
    sys.error("This should never be called")
}