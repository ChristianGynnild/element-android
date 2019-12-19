/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.riotx.features.autocomplete.room

import com.airbnb.epoxy.TypedEpoxyController
import im.vector.matrix.android.api.session.room.model.RoomSummary
import im.vector.matrix.android.api.util.toMatrixItem
import im.vector.riotx.features.autocomplete.AutocompleteClickListener
import im.vector.riotx.features.autocomplete.autocompleteMatrixItem
import im.vector.riotx.features.home.AvatarRenderer
import javax.inject.Inject

class AutocompleteRoomController @Inject constructor() : TypedEpoxyController<List<RoomSummary>>() {

    var listener: AutocompleteClickListener<RoomSummary>? = null

    @Inject lateinit var avatarRenderer: AvatarRenderer

    override fun buildModels(data: List<RoomSummary>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        data.forEach { roomSummary ->
            autocompleteMatrixItem {
                id(roomSummary.roomId)
                matrixItem(roomSummary.toMatrixItem())
                subName(roomSummary.canonicalAlias)
                avatarRenderer(avatarRenderer)
                clickListener { _ ->
                    listener?.onItemClick(roomSummary)
                }
            }
        }
    }
}
