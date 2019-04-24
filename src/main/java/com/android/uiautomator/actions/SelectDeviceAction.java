/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.uiautomator.actions;

import com.android.ddmlib.IDevice;
import com.android.uiautomator.DebugBridge;
import com.android.uiautomator.UiAutomatorHelper;
import com.android.uiautomator.UiAutomatorView;
import com.android.uiautomator.UiAutomatorViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;

import java.util.List;

public class SelectDeviceAction extends Action {

    private UiAutomatorViewer mViewer;

    public SelectDeviceAction(UiAutomatorViewer viewer) {
        super("&Select Device");
        mViewer = viewer;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return ImageHelper.loadImageDescriptorFromResource("images/device.png");
    }

    @Override
    public void run() {
        IDevice device = pickDevice();
        UiAutomatorHelper.setDevice(device);
    }

    private IDevice pickDevice() {
        List<IDevice> devices = DebugBridge.getDevices();
        if (devices.size() == 0) {
            MessageDialog.openError(mViewer.getShell(),
                    "Error obtaining Device Screenshot",
                    "No Android devices were detected by adb.");
            return null;
        } else if (devices.size() == 1) {
            return devices.get(0);
        } else {
            ScreenshotAction.DevicePickerDialog dlg = new ScreenshotAction.DevicePickerDialog(mViewer.getShell(), devices);
            if (dlg.open() != Window.OK) {
                return null;
            }
            return dlg.getSelectedDevice();
        }
    }
}
