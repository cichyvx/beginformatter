package com.github.cichyvx.beginformatter;

import java.util.LinkedList;

public class BeginEndFormatter {

    public static final String LINE_BREAK = "\n";
    public static final String BEGIN = "BEGIN";
    public static final String END = "END";
    public static final String FIRST_CHARACTERS_AFTER_WHITESPACES_REGEX = "[^ \t].*";
    public static final String WHITESPACES_BEFORE_FIRST_CHARACTER_REGEX = "^[ \t]+";
    public static final String EMPTY = "";
    public static final String TAB = "\t";

    public String format(String text) {
        String[] lines = text.split(LINE_BREAK);
        StringBuilder refactorResult = new StringBuilder();
        LinkedList<String> indentationList = new LinkedList<>();

        for (int i = 0; i < lines.length; i++) {
            if (lineIsBegin(lines[i])) {
                lines[i] = processForBegin(i, lines, indentationList);
            } else if (lineIsEnd(lines[i])) {
                lines[i] = processForEnd(i, lines, indentationList);
            } else if (lineIsBetweenBeginEnd(indentationList)){
                lines[i] = processForBetweenLines(lines, i, indentationList);
            }
            refactorResult.append(lines[i]).append(LINE_BREAK);
        }

        return refactorResult.toString();
    }

    private boolean lineIsBegin(String lines) {
        return lines.contains(BEGIN);
    }

    private boolean lineIsEnd(String lines) {
        return lines.contains(END);
    }

    private boolean lineIsBetweenBeginEnd(LinkedList<String> indentationList) {
        return !indentationList.isEmpty();
    }

    private String processForBegin(int i, String[] lines, LinkedList<String> indentationList) {
        if (documentStartWithBegin(i)) {
            indentationList.add(EMPTY);
            return BEGIN;
        } else {
            int previous = i - 1;
            String indentation = lines[previous].replaceAll(FIRST_CHARACTERS_AFTER_WHITESPACES_REGEX, EMPTY);
            String newLine = indentation + BEGIN + getRestOfLine(lines[i], true);
            indentationList.add(indentation);
            return newLine;
        }
    }

    private String processForEnd(int i, String[] lines, LinkedList<String> indentationList) {
        return indentationList.removeLast() + END + getRestOfLine(lines[i], false);
    }

    private String processForBetweenLines(String[] lines, int i, LinkedList<String> indentationList) {
        return String.join(EMPTY, indentationList) +
                TAB +
                lines[i].replaceAll(WHITESPACES_BEFORE_FIRST_CHARACTER_REGEX, EMPTY);
    }

    private boolean documentStartWithBegin(int i) {
        return i == 0;
    }

    private String getRestOfLine(String line, boolean isBegin) {
        String[] after = line.split(isBegin ? BEGIN : END);
        return after.length <= 1 ? EMPTY : after[1];
    }

}
