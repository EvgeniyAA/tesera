package com.tesera.feature.media

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tesera.core.extensions.openFile
import com.tesera.core.extensions.openInBrowser
import com.tesera.core.extensions.share
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.MediaOptionItem
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Link
import com.tesera.domain.model.MediaModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    mediaModel: MediaModel?,
    sheetState: ModalBottomSheetState,
    onStartDownload: (GameFile) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            when (mediaModel) {
                is GameFile -> FileOptions(mediaModel, onStartDownload)
                is Link -> LinkOptions(mediaModel)
                null -> Unit
            }
        }
    ) { content() }
}

@Composable
fun FileOptions(
    gameFile: GameFile,
    onStartDownload: (GameFile) -> Unit,
) {
    val share = stringResource(id = R.string.share)
    val context = LocalContext.current
    Text(
        gameFile.title,
        style = AppTheme.typography.heading5,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    if (gameFile.content.isNotEmpty())
        Text(
            gameFile.content,
            style = AppTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    MediaOptionItem(title = share, R.drawable.ic_share) {
        share(share, gameFile.photoUrl, context)
    }

    MediaOptionItem(
        title = stringResource(id = R.string.open_in_browser),
        R.drawable.ic_eye
    ) {
        openInBrowser(gameFile.photoUrl, context)
    }
    when (val status = gameFile.downloadStatus) {
        DownloadStatus.CanBeDownloaded -> MediaOptionItem(
            title = stringResource(id = R.string.download),
            R.drawable.ic_download,
        ) { onStartDownload(gameFile) }

        is DownloadStatus.Downloaded -> {
            MediaOptionItem(
                title = stringResource(id = R.string.open),
                icon = R.drawable.ic_file_open
            ) { openFile(status.path, context) }
        }

        is DownloadStatus.Downloading -> MediaOptionItem(
            title = stringResource(id = R.string.download),
            null, progress = status.progress
        ) {}

        is DownloadStatus.Error -> MediaOptionItem(
            title = stringResource(id = R.string.download),
            R.drawable.ic_error,
        ) { onStartDownload(gameFile) }
    }
}

@Composable
fun LinkOptions(
    link: Link,
) {
    val share = stringResource(id = R.string.share)
    val context = LocalContext.current
    Text(
        link.title,
        style = AppTheme.typography.heading5,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    MediaOptionItem(title = share, R.drawable.ic_share) {
        share(share, link.photoUrl, context)
    }

    MediaOptionItem(
        title = stringResource(id = R.string.open_in_browser),
        R.drawable.ic_eye
    ) {
        openInBrowser(link.photoUrl, context)
    }
}