/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-mobile/blob/master/LICENSE.md
 */
package org.mifos.mobile.feature.user.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mifos_mobile.feature.user_profile.generated.resources.Res
import mifos_mobile.feature.user_profile.generated.resources.account_number
import mifos_mobile.feature.user_profile.generated.resources.activation_date
import mifos_mobile.feature.user_profile.generated.resources.change_password
import mifos_mobile.feature.user_profile.generated.resources.client_classification
import mifos_mobile.feature.user_profile.generated.resources.client_type
import mifos_mobile.feature.user_profile.generated.resources.found
import mifos_mobile.feature.user_profile.generated.resources.gender
import mifos_mobile.feature.user_profile.generated.resources.groups
import mifos_mobile.feature.user_profile.generated.resources.ic_keyboard_arrow_right_black_24dp
import mifos_mobile.feature.user_profile.generated.resources.no
import mifos_mobile.feature.user_profile.generated.resources.no_dob_found
import mifos_mobile.feature.user_profile.generated.resources.not_assigned_with_any_group
import mifos_mobile.feature.user_profile.generated.resources.office_name
import mifos_mobile.feature.user_profile.generated.resources.phone_number
import mifos_mobile.feature.user_profile.generated.resources.user_details
import mifos_mobile.feature.user_profile.generated.resources.username
import org.koin.compose.viewmodel.koinViewModel
import org.mifos.mobile.core.designsystem.component.MifosScaffold
import org.mifos.mobile.core.model.entity.client.Client
import org.mifos.mobile.core.model.entity.client.Group
import org.mifos.mobile.core.ui.component.MifosErrorComponent
import org.mifos.mobile.core.ui.component.MifosProgressIndicatorOverlay
import org.mifos.mobile.core.ui.component.MifosUserImage
import org.mifos.mobile.core.ui.component.UserProfileField
import org.mifos.mobile.core.ui.component.UserProfileTopBar
import org.mifos.mobile.feature.user.profile.viewmodel.ProfileState.ViewState
import org.mifos.mobile.feature.user.profile.viewmodel.UserDetailViewModel

@Composable
internal fun UserProfileScreen(
    navigateBack: () -> Unit,
    changePassword: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.clientState.collectAsStateWithLifecycle()
    val isConnected by viewModel.isNetworkAvailable.collectAsStateWithLifecycle()

    MifosScaffold(
        topBar = {
            UserProfileTopBar(home = navigateBack, text = Res.string.user_details)
        },
        snackbarHostState = remember { SnackbarHostState() },
        modifier = modifier,
        content = { paddingValues ->
            when (uiState) {
                is ViewState.Error -> {
                    MifosErrorComponent(
                        isNetworkConnected = isConnected,
                        isRetryEnabled = true,
                        onRetry = viewModel::retry,
                        message = (uiState as ViewState.Error).message.toString(),
                    )
                }
                is ViewState.Loading -> {
                    MifosProgressIndicatorOverlay()
                }
                is ViewState.Success -> {
                    UserProfileContent(
                        client = (uiState as ViewState.Success).client,
                        image = TODO(),
                        changePassword = changePassword,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
            }
        },
    )
}

@Composable
private fun UserProfileContent(
    client: Client,
    image: ImageBitmap,
    changePassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userName = client.displayName ?: "-"
    val accountNumber = client.accountNo ?: "-"
    val activationDate = nullFieldCheck(
        Res.string.activation_date.toString(),
        client.activationDate.toString(),
    )
    val officeName = client.officeName ?: "-"
    val clientType = client.clientType?.name ?: "-"
    val groups = getGroups(client.groups)
    val clientClassification = client.clientClassification?.name ?: "-"
    val phoneNumber = nullFieldCheck(Res.string.phone_number.toString(), client.mobileNo)
    val dob = if (client.dobDate.size != 3) {
        Res.string.no_dob_found.toString()
    } else {
        client.dobDate.joinToString("-")
    }
    val gender = client.gender?.name ?: "-"

    val userDetails = Client(
        displayName = userName,
        accountNo = accountNumber,
        activationDate = TODO(),
        officeName = officeName,
        clientType = TODO(),
        groups = TODO(),
        clientClassification = TODO(),
//        phoneNumber = phoneNumber,
//        dob = dob,
        gender = TODO(),
    )

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            MifosUserImage(
                modifier = Modifier.size(100.dp),
                bitmap = TODO(),
                username = userName,
            )
        }
        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        UserProfileField(label = Res.string.username, value = userName)
        UserProfileField(label = Res.string.account_number, value = accountNumber)
        UserProfileField(label = Res.string.activation_date, value = activationDate)
        UserProfileField(label = Res.string.office_name, value = officeName)
        UserProfileField(label = Res.string.groups, value = groups)
        UserProfileField(label = Res.string.client_type, value = clientType)
        UserProfileField(label = Res.string.client_classification, value = clientClassification)
        UserProfileField(label = Res.string.phone_number, value = phoneNumber)
        UserProfileField(label = Res.string.no_dob_found, value = dob)
        UserProfileField(label = Res.string.gender, value = gender)
        UserProfileField(
            text = Res.string.change_password,
            icon = Res.drawable.ic_keyboard_arrow_right_black_24dp,
            onClick = changePassword,
        )
    }
}

private fun nullFieldCheck(field: String, value: String?): String {
    return value ?: (Res.string.no.toString() + " " + field + " " + Res.string.found)
}

private fun getGroups(groups: List<Group>?): String {
    if (groups.isNullOrEmpty()) {
        return Res.string.not_assigned_with_any_group.toString()
    }
    return groups.joinToString(" | ") { it.name.toString() }
}

/**
 * Decodes the [ByteArray] (if available) into an [ImageBitmap]. If no data is present or decoding fails,
 * a default placeholder image is returned.
 */
@Composable
private fun getUserImage(imageData: ByteArray?): ImageBitmap {
    return if (imageData != null) {
        try {
            TODO()
        } catch (e: Exception) {
            defaultPlaceholderImage()
        }
    } else {
        defaultPlaceholderImage()
    }
}

@Composable
private fun defaultPlaceholderImage(): ImageBitmap {
    return ImageBitmap(100, 100)
}
