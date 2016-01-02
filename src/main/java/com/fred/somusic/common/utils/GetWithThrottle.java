package com.fred.somusic.common.utils;

import java.io.IOException;

public class GetWithThrottle {

	// 10 request / second / IP
	private final int maxRequestPerSeconds = 10;
	private final int maxBatchDuration = 1000;

	private long batchStart;
	private int countSinceLastReset;
	private String basicAuthHeader;
	private static int maxRetry = 10;

	@SuppressWarnings("restriction")
	public void setBasicAuthorization(String username, String password) {
		String userpass = username + ":" + password;
		basicAuthHeader = "Basic "
				+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass
						.getBytes());
	}

	public synchronized String getBody(String request) throws IOException {
		String body;

		int retryCount = 0;
		boolean retry;
		do {
			retry = false;
			body = getBody0(request);

			if (body.contains("You have been rate limited. Enhance your calm.")
					|| "You hit the rate limit, wait 10 seconds and try again"
							.equals(body)) {
				retry = true;
				retryCount++;

				// PENDING(fredL) handle this better...
				if (retryCount == maxRetry) {
					return "";
				}

				if (retryCount % 10 == 0) {
					Log.severe("throttle", "Limit reached for " + request
							+ ". Retried for " + retryCount);
				}

				try {
					Thread.sleep(11000);
				} catch (InterruptedException e) {
				}
			}

		} while (retry);

		return body;
	}

	private String getBody0(String request) throws IOException {
		long currentTime = System.currentTimeMillis();
		if (countSinceLastReset == 0) {
			batchStart = currentTime;
		}

		long batchDuration = currentTime - batchStart;
		double requestPerSeconds = 0;

		if (batchDuration > 0) {
			requestPerSeconds = (double) countSinceLastReset * maxBatchDuration
					/ batchDuration;
			Log.fine("current rate " + countSinceLastReset + " requests in "
					+ batchDuration + "ms aka " + requestPerSeconds
					+ " requests/" + maxBatchDuration + "ms");
		}

		// limit reached
		if (countSinceLastReset == maxRequestPerSeconds) {
			// too many requests in so little time, pause
			if (batchDuration < 1000) {
				try {
					Log.fine("Reached throttle limit in " + batchDuration
							+ "ms while getting " + request + ". Sleeping...");
					Thread.sleep(maxBatchDuration + 1000 - batchDuration);
				} catch (InterruptedException e) {
				}
			}

			// and reset
			batchStart = System.currentTimeMillis();
			countSinceLastReset = 0;
		}

		countSinceLastReset++;
		return basicAuthHeader == null ? Utils.getBody(request) : Utils
				.getBodyWithAuthentication(request, basicAuthHeader);
	}
}