package server;

import socket.BaseConnector;

import socket.EventSender;
import event.*;
import common.Config;
import web.Client;

public class test {
	public static void main(String args[]) {
		String s = "123";
		new Thread() {
			@Override
			public void run() {
				synchronized (s) {
					while (true);
				}
			}
		}.start();
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		new Thread() {
			@Override
			public void run() {
				synchronized (s) {
				System.out.println(s);}
			}
		}.start();
	}
}
