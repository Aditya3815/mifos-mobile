/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-mobile/blob/master/LICENSE.md
 */
package org.mifos.mobile.feature.user.profile.component

/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mifos_mobile.feature.user_profile.generated.resources.Res
import mifos_mobile.feature.user_profile.generated.resources.feature_profile_email
import mifos_mobile.feature.user_profile.generated.resources.feature_profile_mobile
import mifos_mobile.feature.user_profile.generated.resources.feature_profile_username
import mifos_mobile.feature.user_profile.generated.resources.feature_profile_vpa
import org.jetbrains.compose.resources.stringResource
import org.mifos.mobile.core.model.entity.client.Client

@Composable
fun ProfileDetailsCard(
    client: Client,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProfileItem(
                label = stringResource(Res.string.feature_profile_username),
                value = client.displayName.toString(),
            )
            ProfileItem(
                label = stringResource(Res.string.feature_profile_email),
                value = client,
            )
            ProfileItem(
                label = stringResource(Res.string.feature_profile_vpa),
                value = client.externalId.toString(),
            )
            ProfileItem(
                label = stringResource(Res.string.feature_profile_mobile),
                value = client.mobileNo.toString(),
            )
        }
    }
}

@Composable
fun ProfileItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight(400),
        )
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
        )
    }
}
