package com.github.cichyvx.beginformatter;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;

import java.util.LinkedList;

public class BeginEndFormatter {

    public static final String LINE_BREAK = "\n";
    public static final String BEGIN = "BEGIN";
    public static final String END = "END";
    public static final String FIRTS_NO_WHITESPACE_CHARACTER_REGEX = "[^ \t].*";
    public static final String EMPTY = "";

    public void format(Document document, Project project) {
        String[] lines = document.getText().split(LINE_BREAK);
        StringBuilder content = new StringBuilder();
        LinkedList<String> beginList = new LinkedList<>();

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(BEGIN)) {
                processForBegin(i, content, lines, beginList);
            } else if (lines[i].contains(END)) {
                processForEnd(i, content, lines, beginList);
            } else {
                content.append(lines[i]);
            }

            content.append(LINE_BREAK);
        }

        WriteCommandAction.runWriteCommandAction(project, () -> document.setText(content));
    }

    private void processForEnd(int i, StringBuilder content, String[] lines, LinkedList<String> beginList) {
        content.append(beginList.getLast())
                .append(END)
                .append(getRestOfStringOrEmpty(lines[i], false));

        beginList.removeLast();
    }

    private void processForBegin(int i, StringBuilder content, String[] lines, LinkedList<String> beginList) {
        if (documentStartWithBegin(i)) {
            content.append(BEGIN);
            beginList.add(EMPTY);
        } else {
            int previousIndex = i - 1;
            String strBeforeBegin = lines[previousIndex].replaceAll(FIRTS_NO_WHITESPACE_CHARACTER_REGEX, EMPTY);
            content.append(strBeforeBegin).append(BEGIN).append(getRestOfStringOrEmpty(lines[i], true));
            beginList.add(strBeforeBegin);
        }
    }

    private String getRestOfStringOrEmpty(String line, boolean isBegin) {
        String[] after = line.split(isBegin ? BEGIN : END);
        return after.length <= 1 ? EMPTY : after[1];
    }

    private boolean documentStartWithBegin(int i) {
        return i == 0;
    }

}
