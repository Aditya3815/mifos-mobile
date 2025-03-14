/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-mobile/blob/master/LICENSE.md
 */
package org.mifos.mobile.feature.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import mifos_mobile.feature.location.generated.resources.Res
import mifos_mobile.feature.location.generated.resources.mifos_initiative
import mifos_mobile.feature.location.generated.resources.mifos_location
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LocationsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.mifos_initiative),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(Res.string.mifos_location),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        val headquarterLatLng = LatLng(47.61115, -122.34481)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(headquarterLatLng, 16f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = headquarterLatLng),
                title = Res.string.mifos_initiative.toString(),
                snippet = Res.string.mifos_location.toString(),
            )
        }
    }
}

//@Composable
//@DevicePreview
//private fun PreviewLocationsScreen() {
//    MifosMobileTheme {
//        LocationsScreen()
//    }
//}
