package com.github.sundyloveme.msgpackviewer.services

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class MsgpackData : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val clipboardData = clipboard.getData(DataFlavor.stringFlavor) as String
        val firstLine = clipboardData.lines().firstOrNull() ?: ""

        Messages.showMessageDialog("First line of clipboard data: $firstLine", "Clipboard Data", Messages.getInformationIcon())
    }
}
