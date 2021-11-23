package com.noah.app.constants;

public enum CorpSize {
	Large{
		public String toString() {
			return "CPC_CAPITAL_LARGE";
		}
	},
	Middle{
		public String toString() {
			return "CPC_CAPITAL_MIDDLE";
			}
		},
	Small{
		public String toString() {
			return "CPC_CAPITAL_SMALL";
			}
		},
	Null{
		public String toString() {
			return "CPC_CAPITAL_NULL";
		}
	};
}
