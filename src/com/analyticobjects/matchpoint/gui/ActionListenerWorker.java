package com.analyticobjects.matchpoint.gui;

import java.awt.event.*;
/**
 *
 * @author Joel
 * @version 1.0, 12/17/2006
 */
public interface ActionListenerWorker
{
    public void actionPerformedInWorkerThread(ActionEvent ae);
    public void actionPerformedWhenDone(ActionEvent ae);
}
