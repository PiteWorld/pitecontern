LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

TARGET_PLATFORM := android-3
LOCAL_MODULE    := serial_port
LOCAL_SRC_FILES := SerialPort.c
LOCAL_LDLIBS    := -llog
LOCAL_LDFLAGS += -fuse-ld=bfd

include $(BUILD_SHARED_LIBRARY)