package edu.pse.beast.codearea.errorhandling;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;

/**
 *  Implements a simple highlight painter that renders a rectangle around the
 *  area to be highlighted.
 *  @author https://tips4java.wordpress.com/2008/10/28/rectangle-painter/
 **/
public class SquigglePainter extends DefaultHighlighter.DefaultHighlightPainter {
    private static final int SQUIGGLE = 2;
    private static final int ARC = 180;

    public SquigglePainter(final Color color) {
        super(color);
    }

    /**
     * Paints a portion of a highlight.
     *
     * @param g      the graphics context
     * @param offs0  the starting model offset >= 0
     * @param offs1  the ending model offset >= offs1
     * @param bounds the bounding box of the view, which is not necessarily the
     *               region to paint.
     * @param c      the editor
     * @param view   View painting for
     * @return region drawing occurred in
     */
    public Shape paintLayer(final Graphics g,
                            final int offs0,
                            final int offs1,
                            final Shape bounds,
                            final JTextComponent c,
                            final View view) {
        Rectangle r = getDrawingArea(offs0, offs1, bounds, view);
        if (r == null) {
            return null;
        }
        // Do your custom painting
        Color color = getColor();
        g.setColor(color == null ? c.getSelectionColor() : color);
        // Draw the squiggles
        final int twoSquiggles = SQUIGGLE * 2;
        int y = r.y + r.height - SQUIGGLE;
        for (int x = r.x; x <= r.x + r.width - twoSquiggles; x += twoSquiggles) {
            g.drawArc(x, y, SQUIGGLE, SQUIGGLE, 0, ARC);
            g.drawArc(x + SQUIGGLE, y, SQUIGGLE, SQUIGGLE, ARC, ARC + 1);
        }
        // Return the drawing area
        return r;
    }

    private Rectangle getDrawingArea(final int offs0,
                                     final int offs1,
                                     final Shape bounds,
                                     final View view) {
        // Contained in view, can just use bounds.
        if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
            Rectangle alloc;
            if (bounds instanceof Rectangle) {
                alloc = (Rectangle) bounds;
            } else {
                alloc = bounds.getBounds();
            }
            return alloc;
        } else {
            // Should only render part of View.
            try {
                // --- determine locations ---
                Shape shape
                    = view.modelToView(offs0, Position.Bias.Forward,
                                       offs1, Position.Bias.Backward,
                                       bounds);
                Rectangle r = (shape instanceof Rectangle)
                        ? (Rectangle) shape : shape.getBounds();
                return r;
            } catch (BadLocationException e) {
                // cannot render
            }
        }
        // Cannot render
        return null;
    }
}
