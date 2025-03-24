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

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity

object LogManager {
    private val logWriter: LogWriter = CommonWriter()

    private val loggerConfig = object : LoggerConfig {
        override val logWriterList: List<LogWriter>
            get() = listOf(logWriter)
        override val minSeverity: Severity = Severity.Debug
    }

    val logger: Logger = Logger(config = loggerConfig).withTag("Mifos")
}
