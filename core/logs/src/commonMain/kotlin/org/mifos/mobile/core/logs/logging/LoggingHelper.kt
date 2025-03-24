/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-mobile/blob/master/LICENSE.md
 */
package org.mifos.mobile.core.logs.logging

import co.touchlab.kermit.Logger

class LoggingHelper(private val logger: Logger = LogManager.logger) {
    fun debug(tag: String, message: String) = logger.d(tag = tag) { message }
    fun error(tag: String, message: String, throwable: Throwable? = null) {
        logger.e(tag = tag, throwable = throwable) { message }
    }
}
