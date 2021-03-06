package com.marksill.social.instance;

import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.marksill.social.Social;

import net.java.games.input.Controller;

public class InstancePlayer extends Instance {
	
	public static final String CLASS_NAME = "Player";
	public static long pid = -1;
	public static InstanceCamera camera = new InstanceCamera(Instance.game);
	
	public int cid;
	
	private List<LuaValue> keyboardDownCallbacks;
	private List<LuaValue> keyboardUpCallbacks;
	private List<LuaValue> controllerCallbacks;
	private List<LuaValue> mousePressCallbacks;
	private List<LuaValue> mouseReleaseCallbacks;
	private List<LuaValue> mouseClickCallbacks;
	private List<LuaValue> mouseMovedCallbacks;
	private List<LuaValue> mouseWheelCallbacks;
	private List<LuaValue> removedCallbacks;

	public InstancePlayer() {
		super(CLASS_NAME);
	}

	public InstancePlayer(String name) {
		super(name);
	}

	public InstancePlayer(Instance parent) {
		super(CLASS_NAME, parent);
	}

	public InstancePlayer(String name, Instance parent) {
		super(name, parent);
	}
	
	@Override
	public void init() {
		keyboardDownCallbacks = new ArrayList<LuaValue>();
		keyboardUpCallbacks = new ArrayList<LuaValue>();
		controllerCallbacks = new ArrayList<LuaValue>();
		mousePressCallbacks = new ArrayList<LuaValue>();
		mouseReleaseCallbacks = new ArrayList<LuaValue>();
		mouseClickCallbacks = new ArrayList<LuaValue>();
		mouseMovedCallbacks = new ArrayList<LuaValue>();
		mouseWheelCallbacks = new ArrayList<LuaValue>();
		removedCallbacks = new ArrayList<>();
		if (Social.getInstance().isNetworked() && Social.getInstance().isServer()) {
			new InstanceCamera(this);
		}
	}
	
	public void addKeyboardDownCallback(LuaValue func) {
		keyboardDownCallbacks.add(func);
	}
	
	public void fireKeyboardDownCallbacks(int key, char c) {
		for (LuaValue v : keyboardDownCallbacks) {
			v.call(LuaValue.valueOf(key), LuaValue.valueOf(c));
		}
	}
	
	public void clearKeyboardDownCallbacks() {
		keyboardDownCallbacks.clear();
	}
	
	public void addKeyboardUpCallback(LuaValue func) {
		keyboardUpCallbacks.add(func);
	}
	
	public void fireKeyboardUpCallbacks(int key, char c) {
		for (LuaValue v : keyboardUpCallbacks) {
			v.call(LuaValue.valueOf(key), LuaValue.valueOf(c));
		}
	}
	
	public void clearKeyboardUpCallbacks() {
		keyboardUpCallbacks.clear();
	}
	
	public void addControllerCallback(LuaValue func) {
		controllerCallbacks.add(func);
	}
	
	public void fireControllerCallbacks(Controller controller, String name, Object data) {
		for (LuaValue v : controllerCallbacks) {
			v.call(CoerceJavaToLua.coerce(controller), LuaValue.valueOf(name), CoerceJavaToLua.coerce(data));
		}
	}
	
	public void clearControllerCallbacks() {
		controllerCallbacks.clear();
	}
	
	public void addMousePressCallback(LuaValue func) {
		mousePressCallbacks.add(func);
	}
	
	public void fireMousePressCallbacks(int button, float x, float y) {
		for (LuaValue v : mousePressCallbacks) {
			v.call(LuaValue.valueOf(button), LuaValue.valueOf(x), LuaValue.valueOf(y));
		}
	}
	
	public void clearMousePressCallbacks() {
		mousePressCallbacks.clear();
	}
	
	public void addMouseReleaseCallback(LuaValue func) {
		mouseReleaseCallbacks.add(func);
	}
	
	public void fireMouseReleaseCallbacks(int button, float x, float y) {
		for (LuaValue v : mouseReleaseCallbacks) {
			v.call(LuaValue.valueOf(button), LuaValue.valueOf(x), LuaValue.valueOf(y));
		}
	}
	
	public void clearMouseReleaseCallbacks() {
		mouseReleaseCallbacks.clear();
	}
	
	public void addMouseClickCallback(LuaValue func) {
		mouseClickCallbacks.add(func);
	}
	
	public void fireMouseClickCallbacks(int button, float x, float y, int clickCount) {
		for (LuaValue v : mouseClickCallbacks) {
			v.invoke(LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(button), LuaValue.valueOf(x), LuaValue.valueOf(y), LuaValue.valueOf(clickCount)}));
		}
	}
	
	public void clearMouseClickCallbacks() {
		mouseClickCallbacks.clear();
	}
	
	public void addMouseMovedCallback(LuaValue func) {
		mouseMovedCallbacks.add(func);
	}
	
	public void fireMouseMovedCallbacks(float oldX, float oldY, float x, float y) {
		for (LuaValue v : mouseMovedCallbacks) {
			v.invoke(LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(oldX), LuaValue.valueOf(oldY), LuaValue.valueOf(x), LuaValue.valueOf(y)}));
		}
	}
	
	public void clearMouseMovedCallbacks() {
		mouseMovedCallbacks.clear();
	}
	
	public void addMouseWheelCallback(LuaValue func) {
		mouseWheelCallbacks.add(func);
	}
	
	public void fireMouseWheelCallbacks(int newValue) {
		for (LuaValue v : mouseWheelCallbacks) {
			v.call(LuaValue.valueOf(newValue));
		}
	}
	
	public void clearMouseWheelCallbacks() {
		mouseWheelCallbacks.clear();
	}
	
	public void addRemovedCallback(LuaValue v) {
		removedCallbacks.add(v);
	}
	
	public void clearRemovedCallbacks() {
		removedCallbacks.clear();
	}
	
	public void clearCallbacks() {
		clearKeyboardDownCallbacks();
		clearKeyboardUpCallbacks();
		clearControllerCallbacks();
		clearMousePressCallbacks();
		clearMouseReleaseCallbacks();
		clearMouseMovedCallbacks();
		clearMouseWheelCallbacks();
		clearRemovedCallbacks();
	}
	
	@Override
	public void delete() {
		for (LuaValue v : removedCallbacks) {
			if (v != null) {
				v.call(CoerceJavaToLua.coerce(this));
			}
		}
		super.delete();
	}

}
