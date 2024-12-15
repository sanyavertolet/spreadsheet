package io.github.sanyavertolet.interview.modals;

import javax.swing.*;
import java.awt.*;

/**
 * A utility class for displaying a non-blocking "Loading" dialog in a Swing application.
 *
 * <p>The {@link LoadingDialog} provides a simple visual feedback mechanism to inform users
 * that a background operation is in progress. It shows a modal-less dialog with a customizable
 * message and title, which can be displayed or hidden programmatically.</p>
 */
public class LoadingDialog {
    private final JDialog dialog;

    /**
     * Creates a new {@code LoadingDialog} with a custom message and title.
     *
     * <p>This constructor initializes a non-blocking {@link JDialog} with the specified message
     * and title. The dialog cannot be closed by the user, as it is meant to indicate that a
     * background operation is ongoing. It centers itself on the screen by default.</p>
     *
     * @param message the message to display in the dialog.
     * @param title   the title of the dialog window.
     */
    public LoadingDialog(String message, String title) {
        dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.add(label, BorderLayout.CENTER);

        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(null);
    }

    /**
     * Creates a new {@code LoadingDialog} with a custom message and a default title of "Loading".
     *
     * @param message the message to display in the dialog.
     */
    public LoadingDialog(String message) {
        this(message, "Loading");
    }

    /**
     * Displays the dialog.
     *
     * <p>This method makes the dialog visible on the screen. It uses
     * {@link SwingUtilities#invokeLater(Runnable)} to ensure the dialog is displayed
     * on the Event Dispatch Thread (EDT).</p>
     */
    public void showDialog() {
        SwingUtilities.invokeLater(() -> dialog.setVisible(true));
    }

    /**
     * Hides and disposes of the dialog.
     *
     * <p>This method closes the dialog and releases its resources. It uses
     * {@link SwingUtilities#invokeLater(Runnable)} to ensure the operation
     * is performed on the Event Dispatch Thread (EDT).</p>
     */
    public void hideDialog() {
        SwingUtilities.invokeLater(dialog::dispose);
    }
}
