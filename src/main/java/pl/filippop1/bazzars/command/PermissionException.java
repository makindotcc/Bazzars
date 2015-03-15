/*
 * Copyright 2014 Aleksander.
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
package pl.filippop1.bazzars.command;

public class PermissionException extends CommandException {
    /**
     * Constructs an instance of <code>PermissionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PermissionException(String msg) {
        super("Brak odpowiednich uprawnien (" + msg + "), aby wykonac ta komende.");
    }
}
