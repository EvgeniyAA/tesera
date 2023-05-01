package com.tesera.feature.media

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tesera.core.extensions.openFile
import com.tesera.core.extensions.openInBrowser
import com.tesera.core.extensions.share
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.MediaOptionItem
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.FileModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetOptionsScreen(
    fileModel: FileModel?,
    modalBottomSheetValue: BottomSheetScaffoldState,
    onStartDownload: (url: String) -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val share = stringResource(id = R.string.share)
    BottomSheetScaffold(
        sheetContent = {
            if (fileModel != null) {
                Text(
                    fileModel.title,
                    style = AppTheme.typography.heading5,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                if(fileModel.content.isNotEmpty())
                    Text(
                        fileModel.content,
                        style = AppTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                MediaOptionItem(title = share, R.drawable.ic_share) {
                    share(share, fileModel.photoUrl, context)
                }

                MediaOptionItem(
                    title = stringResource(id = R.string.open_in_browser),
                    R.drawable.ic_eye
                ) {
                    openInBrowser(fileModel.photoUrl, context)
                }
                when (val status = fileModel.downloadStatus) {
                    DownloadStatus.CanBeDownloaded -> MediaOptionItem(
                        title = stringResource(id = R.string.download),
                        R.drawable.ic_download,
                    ) { onStartDownload(fileModel.photoUrl) }

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
                    ) { onStartDownload(fileModel.photoUrl) }
                }
            }
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = modalBottomSheetValue
    ) { content() }
}
