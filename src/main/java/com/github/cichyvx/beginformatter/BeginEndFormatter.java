package com.github.cichyvx.beginformatter;

import java.util.LinkedList;
import java.util.stream.Collectors;

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
        StringBuilder content = new StringBuilder();
        LinkedList<String> beginList = new LinkedList<>();

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(BEGIN)) {
                lines[i] = processForBegin(i, content, lines, beginList);
            } else if (lines[i].contains(END)) {
                lines[i] = processForEnd(i, content, lines, beginList);
            } else {
                if (lineIsBetweenBeginEnd(beginList)) {
                    processForBetweenLines(lines, i, beginList);
                }
                content.append(lines[i]);
            }

            content.append(LINE_BREAK);
        }

        return content.toString();
    }

    private static void processForBetweenLines(String[] lines, int i, LinkedList<String> beginList) {
        lines[i] = String.join("", beginList) +
                TAB +
                lines[i].replaceAll(WHITESPACES_BEFORE_FIRST_CHARACTER_REGEX, EMPTY);
    }

    private static boolean lineIsBetweenBeginEnd(LinkedList<String> beginList) {
        return !beginList.isEmpty();
    }

    private String processForEnd(int i, StringBuilder content, String[] lines, LinkedList<String> beginList) {
        String newLine = beginList.getLast() + END + getRestOfStringOrEmpty(lines[i], false);
        content.append(newLine);

        beginList.removeLast();
        return newLine;
    }

    private String processForBegin(int i, StringBuilder content, String[] lines, LinkedList<String> beginList) {
        if (documentStartWithBegin(i)) {
            content.append(BEGIN);
            beginList.add(EMPTY);
            return BEGIN;
        } else {
            int previousIndex = i - 1;
            String strBeforeBegin = lines[previousIndex].replaceAll(FIRST_CHARACTERS_AFTER_WHITESPACES_REGEX, EMPTY);
            String newLine = strBeforeBegin + BEGIN + getRestOfStringOrEmpty(lines[i], true);
            content.append(newLine);
            beginList.add(strBeforeBegin);
            return newLine;
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
