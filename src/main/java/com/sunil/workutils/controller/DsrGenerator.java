package com.sunil.workutils.controller;

import java.util.ArrayList;

public class DsrGenerator {

	private static final String SUBMIT_STRING = "Submitted changelists (weekly snippet)";
	private static final String REVIEW_STRING = "Outgoing reviews";
	private static final String CC_STRING = "CC'ed changelists";
	private static final String PENDING_STRING = "Pending changelists";
	private static final String EXPAND_STRING = "Expand";

	private static final String MAIL_HEAD = "Hi Pradeep,\n\nPlease find my DSR.";
	private static final String MAIL_TAIL = "\n\nRegards,\nSunil";
	private static final String MAIL_REVIEW = "\nWorked on task and uploaded for review:";
	private static final String MAIL_SUBMITTED = "\nSubmitted:";
	private static final String MAIL_PENDING = "\nTasks in progress:";

	private ArrayList<String> constructTodaysTasks(String[] taskList) {
		ArrayList<String> todayTasks = new ArrayList<>();
		for (int i = 1; i < taskList.length; i++) {
			String task = taskList[i];
			String[] taskDetails = task.split("\n");
			String cl = null;
			String time = null;
			String desc = null;
			for (int j = 0; j < taskDetails.length; j++) {
				try {
					// TODO remove this try-catch, now cider layout got changed so adding this hack.
					cl = taskDetails[2];
					time = taskDetails[5];
					desc = taskDetails[7];
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
				if (desc.endsWith(".")) {
					// Remove the full stop from desc as its going to be list item
					desc = desc.substring(0, desc.length() - 1);
				}
			}
			if (time != null && Character.isDigit(time.charAt(0))) {
				// Today's work
				todayTasks.add(desc + " (cl/" + cl + ")");
			}
		}
		return todayTasks;
	}

	public String constructDsr(String input) {
		StringBuilder sb = new StringBuilder(input);
		String inReviewStr = sb.substring(sb.indexOf(REVIEW_STRING), sb.indexOf(CC_STRING));
		String pendingStr = sb.substring(sb.indexOf(PENDING_STRING), sb.indexOf(SUBMIT_STRING));
		String submittedStr = sb.substring(sb.indexOf(SUBMIT_STRING));

		String[] inReviewList = inReviewStr.split(EXPAND_STRING);
		String[] pendingList = pendingStr.split(EXPAND_STRING);
		String[] submittedList = submittedStr.split(EXPAND_STRING);
		ArrayList<String> inReview = constructTodaysTasks(inReviewList);
		ArrayList<String> pending = constructTodaysTasks(pendingList);
		ArrayList<String> submitted = constructTodaysTasks(submittedList);

		StringBuilder mail = new StringBuilder();
		mail.append(MAIL_HEAD);
		mail.append("\n");
		if (!submitted.isEmpty()) {
			mail.append(MAIL_SUBMITTED);
			mail.append("\n");
			appendTaskBody(mail, submitted);
		}
		if (!inReview.isEmpty()) {
			mail.append(MAIL_REVIEW);
			mail.append("\n");
			appendTaskBody(mail, inReview);
		}
		if (!pending.isEmpty()) {
			mail.append(MAIL_PENDING);
			mail.append("\n");
			appendTaskBody(mail, pending);
		}
		mail.append(MAIL_TAIL);

		return mail.toString();
	}

	private void appendTaskBody(StringBuilder mail, ArrayList<String> items) {
		for (String item : items) {
			mail.append("\t");
			mail.append(item);
			mail.append("\n");
		}
	}
}