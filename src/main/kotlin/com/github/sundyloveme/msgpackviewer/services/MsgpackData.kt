package com.github.sundyloveme.msgpackviewer.services

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import org.msgpack.core.MessagePack
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.ByteArrayInputStream


class MsgpackData : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val clipboardData = clipboard.getData(DataFlavor.stringFlavor) as String
        val firstLine = clipboardData.lines().firstOrNull() ?: ""

        val byteArray = hexStringToByteArray(firstLine.substring(2))
        val unpacker = MessagePack.newDefaultUnpacker(ByteArrayInputStream(byteArray))
        try {
            val value = unpacker.unpackValue()
            Messages.showMessageDialog("First line of clipboard data: $value", "Clipboard Data", Messages.getInformationIcon())
        } catch (e: Exception) {
            Messages.showMessageDialog("unpack failed. error is : $e", "Clipboard Data", Messages.getInformationIcon())
        } finally {
            unpacker.close()
        }
    }

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}