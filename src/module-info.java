module FinalProjectOOAD {
	opens main;
	opens controller;
	opens view;
	opens model;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	opens database;
	opens helper;
}