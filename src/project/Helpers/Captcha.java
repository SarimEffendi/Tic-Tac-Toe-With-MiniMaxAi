package project.Helpers;

import java.util.Stack;

public class Captcha {

	//Generate random Characters
	public static String generateRandomBrackets() {
		String characters = "{[(";
		StringBuilder captcha = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			int index = (int) ((new java.util.Random()).nextInt(characters.length()));
			captcha.append(characters.charAt(index));
		}
		return captcha.toString();
	}

	// Complexity O(n)
	public static boolean balancedBrackets(String inputString) {
		Stack<Character> bracketsStack = new Stack<Character>();
		try {
			for (int i = 0; i < inputString.length(); i++) {
				char nextChar = inputString.charAt(i);
				if (nextChar == '(' || nextChar == '[' || nextChar == '{' || nextChar == '<') {
					bracketsStack.push(nextChar);
				} else if (nextChar == ')') {
					if (bracketsStack.pop() != '(') {
						return false;
					}
				} else if (nextChar == ']') {
					if (bracketsStack.pop() != '[') {
						return false;
					}
				} else if (nextChar == '}') {
					if (bracketsStack.pop() != '{') {
						return false;
					}
				} else if (nextChar == '>') {
					if (bracketsStack.pop() != '<') {
						return false;
					}
				}
			}
			if (!bracketsStack.empty()) {
				//If the stack is not empty then there are unmatched brackets
				return false;
			}
		} catch (Exception e) {
			//Any error means brackets were not balanced and stack has malfunctioned
			return false;
		}
		return true;
	}
}
