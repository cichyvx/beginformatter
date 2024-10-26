package com.github.cichyvx.beginformatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;


public class ReformatBeginEndAction extends AnAction {

    private final BeginEndFormatter formatter;

    public ReformatBeginEndAction() {
        this.formatter = new BeginEndFormatter();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            Messages.showMessageDialog("Cannot found project", "Error", Messages.getErrorIcon());
            return;
        }

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            Messages.showMessageDialog("Editor not open", "Error", Messages.getErrorIcon());
            return;
        }

        Document document = editor.getDocument();

        WriteCommandAction.runWriteCommandAction(project, () -> document.setText(formatter.format(document.getText())));

    }


}