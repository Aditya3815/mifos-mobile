/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-mobile/blob/master/LICENSE.md
 */
package org.mifos.mobile.feature.user.profile

/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mifos.mobile.core.common.DataState
import org.mifos.mobile.core.data.repository.ClientRepository
import org.mifos.mobile.core.datastore.UserPreferencesRepository
import org.mifos.mobile.core.model.entity.client.Client
import org.mifos.mobile.core.ui.utils.BaseViewModel
import org.mifos.mobile.feature.user.profile.viewmodel.EditProfileAction.Internal.HandleLoadClientImageResult
import org.mifos.mobile.feature.user.profile.viewmodel.EditProfileAction.Internal.LoadClientImage

internal class ProfileViewModel(
    private val preferencesRepository: UserPreferencesRepository,
    private val clientRepository: ClientRepository,
) : BaseViewModel<ProfileState, ProfileEvent, ProfileAction>(
    initialState = run {
        val clientId = requireNotNull(preferencesRepository.clientId.value)
        ProfileState(clientId = clientId)
    },
) {
    val clientState = clientRepository.loadClient().map {
        when (it) {
            is DataState.Loading -> ProfileState.ViewState.Loading
            is DataState.Error -> ProfileState.ViewState.Error(it.exception.message.toString())
            is DataState.Success -> ProfileState.ViewState.Success(it.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileState.ViewState.Loading,
    )

    init {
        viewModelScope.launch {
            sendAction()
        }
    }

    override fun handleAction(action: ProfileAction) {
        when (action) {
            ProfileAction.NavigateToEditProfile -> {
                sendEvent(ProfileEvent.OnEditProfile)
            }

            ProfileAction.NavigateToLinkBankAccount -> {
                sendEvent(ProfileEvent.OnLinkBankAccount)
            }

            ProfileAction.ShowPersonalQRCode -> {
                sendEvent(ProfileEvent.ShowQRCode)
            }

            ProfileAction.DismissErrorDialog -> {
                mutableStateFlow.update {
                    it.copy(dialogState = null)
                }
            }

            is HandleLoadClientImageResult -> handleLoadClientImageResult(action)

            is LoadClientImage -> loadClientImage(action)
            is ProfileAction.Internal.HandleLoadClientImageResult -> TODO()
            is ProfileAction.Internal.LoadClientImage -> TODO()
        }
    }

    private fun handleLoadClientImageResult(action: HandleLoadClientImageResult) {
        when (action.result) {
            is DataState.Success -> {
                mutableStateFlow.update {
                    it.copy(clientImage = action.result.data)
                }
            }

            is DataState.Error -> {
//                mutableStateFlow.update {
//                    it.copy(dialogState = Error(action.result.exception.message ?: ""))
//                }
            }

            is DataState.Loading -> {
                mutableStateFlow.update {
                    it.copy(dialogState = ProfileState.DialogState.Loading)
                }
            }
        }
    }

    private fun loadClientImage(action: LoadClientImage) {
        clientRepository.loadClient().onEach {
            sendAction(action = it as ProfileAction)
        }.launchIn(viewModelScope)
    }
}

internal data class ProfileState(
    val clientId: Long,
    val clientImage: String? = null,
    val dialogState: DialogState? = null,
) {
    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: String) : ViewState
        data class Success(val client: Client) : ViewState
    }

    sealed interface DialogState {
        data object Loading : DialogState

        data class Error(val message: String) : DialogState
    }
}

internal sealed interface ProfileEvent {
    data object OnEditProfile : ProfileEvent
    data object OnLinkBankAccount : ProfileEvent
    data object ShowQRCode : ProfileEvent
}

internal sealed interface ProfileAction {
    data object NavigateToEditProfile : ProfileAction
    data object NavigateToLinkBankAccount : ProfileAction
    data object ShowPersonalQRCode : ProfileAction

    data object DismissErrorDialog : ProfileAction

    sealed interface Internal : ProfileAction {
        data class LoadClientImage(val clientId: Long) : Internal
        data class HandleLoadClientImageResult(val result: DataState<String>) : Internal
    }
}
