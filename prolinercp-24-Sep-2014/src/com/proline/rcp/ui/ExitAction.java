package com.proline.rcp.ui;

import org.eclipse.jface.action.*;
import org.eclipse.jface.window.*;
import org.eclipse.ui.IWorkbenchWindow;

public class ExitAction extends Action
{
	private final IWorkbenchWindow  window;

  public ExitAction(IWorkbenchWindow w)
  {
    window = w;
    setText("E&xit");
  }

  public void run()
  {
    window.close();
  }
}
