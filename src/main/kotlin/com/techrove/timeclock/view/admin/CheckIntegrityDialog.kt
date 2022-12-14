package com.techrove.timeclock.view.admin

import com.techrove.timeclock.Styles
import com.techrove.timeclock.controller.admin.SettingsController
import com.techrove.timeclock.io.Audio
import com.techrove.timeclock.view.custom.IconType
import com.techrove.timeclock.view.custom.timeoutDialog
import com.techrove.timeclock.view.custom.timeoutDialogClose
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow
import tornadofx.*

/**
 * 보안 체크 dialog
 */
fun AdminCenterViewVbox.checkIntegrityDialog() {
    Audio.play("beep1.wav")

    timeoutDialog(
        title = "보안 체크",
        message = "보안 체크를 진행합니다.",
        iconType = IconType.Wait,
        delay = AdminView.defaultTimeout.multiply(2.0),
        buttons = listOf("취소", "체크")
    ) {
        if (it == -1) return@timeoutDialog
        if (it == 1) {
            onCheckClicked()
            timeoutDialog(
                title = "보안 체크",
                message = "보안 체크 중 입니다...",
                iconType = IconType.Wait,
                op = {
                    spacer()
                    progressindicator()
                    spacer()
                }
            ) {
                timeoutDialogClose()
            }
        } else {
            settingsDialog()
        }
    }
}

/**
 * 보안 체크
 */
private fun AdminCenterViewVbox.onCheckClicked() {
    val controller = find(SettingsController::class)
    controller.checkSecurity { swIntegrityOk, keyIntegrityOk, passwordExpiryDaysRemaining, keyExpiryDaysRemaining ->
        timeoutDialog(
            title = "보안 체크",
            iconType =
            if (swIntegrityOk && keyIntegrityOk) IconType.Info else IconType.Error,
            delay = 60.seconds,
            buttons = listOf("닫기"),
            op = {
                fun TextFlow.message(title: String, okMessage: String, failMessage: String? = null, ok: Boolean) {
                    text("$title : ").apply {
                        fill = Color.rgb(0xaa, 0xaa, 0xaa)
                        font = Styles.customFont
                    }
                    if (ok) {
                        text(okMessage + "\n").apply {
                            fill = Color.rgb(0x33, 0x33, 0x33)
                            font = Styles.customFont
                        }
                    } else {
                        text((failMessage ?: okMessage) + "\n").apply {
                            fill = Color.RED
                            font = Styles.customFont
                        }
                    }
                }
                textflow {
                    textAlignment = TextAlignment.CENTER
                    message("SW 무결성", "정상", "오류", swIntegrityOk)
                    message("보안키 무결성", "정상", "오류", keyIntegrityOk)
                    message("보안키 갱신 유효기간", "$keyExpiryDaysRemaining 일 남음", null, true)
                    message(
                        "관리자 암호 유효기간",
                        "$passwordExpiryDaysRemaining 일 남음",
                        null,
                        passwordExpiryDaysRemaining > 90
                    )
                }
            }
        ) {
            settingsDialog()
        }
    }
}
