/**
 * 
 */
package com.kaliuss.tapadu.entidades;

/**
 * @author vablas
 *
 */
public class Invoker {
	
	private Command command;
	
	public void run(){
		command.execute();
	}

}
