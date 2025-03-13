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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mifos_mobile.feature.user_profile.generated.resources.Res
import mifos_mobile.feature.user_profile.generated.resources.ic_cake_24dp
import mifos_mobile.feature.user_profile.generated.resources.ic_gender_24dp
import mifos_mobile.feature.user_profile.generated.resources.ic_phone_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.mifos.mobile.core.designsystem.theme.MifosMobileTheme
import org.mifos.mobile.core.model.entity.client.Client
import org.mifos.mobile.core.model.entity.client.ClientClassification
import org.mifos.mobile.core.model.entity.client.ClientType
import org.mifos.mobile.core.model.entity.client.Gender
import org.mifos.mobile.core.model.entity.client.Group
import org.mifos.mobile.core.ui.utils.DevicePreview

@Composable
internal fun UserProfileDetails(
    userDetails: Client,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 8.dp),
            text = stringResource(Res.string.user_details),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
        )
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                painter = painterResource(Res.drawable.ic_phone_24dp),
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = null,
            )
            if (userDetails.mobileNo != null) {
                Text(
                    text = userDetails.mobileNo!!,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(fontSize = 14.sp),
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                painter = painterResource(Res.drawable.ic_cake_24dp),
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = null,
            )
            if (userDetails.dobDate != null) {
                Text(
                    text = userDetails.dobDate.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(fontSize = 14.sp),
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                painter = painterResource(Res.drawable.ic_gender_24dp),
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = null,
            )
            if (userDetails.gender != null) {
                Text(
                    text = userDetails.gender.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(fontSize = 14.sp),

                )
            }
        }
    }
}

@DevicePreview
@Composable
private fun UserProfileDetailsPreview(
    modifier: Modifier = Modifier,
) {
    MifosMobileTheme {
        UserProfileDetails(
            userDetails = Client(
                displayName = "John Doe",
                accountNo = "123456",
                activationDate = listOf(2021, 1, 1),
                officeName = "Office Name",
                clientType = ClientType(
                    id = 1,
                    name = "Client Type",
                    active = false,
                    mandatory = false,
                ),
                groups = listOf(Group(id = 1, name = "Group Name")),
                clientClassification = ClientClassification(
                    id = 1,
                    name = "Client Classification",
                    active = false,
                    mandatory = false,
                ),
                mobileNo = "1234567890",
                dobDate = listOf(1990, 1, 1),
                gender = Gender(
                    id = 1,
                    name = "Male",
                    active = false,
                    mandatory = false,
                ),
            ),
            modifier = modifier,
        )
    }
}
