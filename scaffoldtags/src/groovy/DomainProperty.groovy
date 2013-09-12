/* Copyright 2007-2009 Daiji Takamori
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class DomainProperty {
    Object prop
    String name
    Object value
    Object style
    Object widget

    String toString() {
        "${super.toString()}[name=${name},value=${value},style=${style},widget=${widget}]"
    }
}
