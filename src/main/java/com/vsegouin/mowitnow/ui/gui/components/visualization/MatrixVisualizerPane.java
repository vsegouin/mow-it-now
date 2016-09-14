package com.vsegouin.mowitnow.ui.gui.components.visualization;

import com.google.inject.Inject;
import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.PositionState;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import com.vsegouin.mowitnow.ui.gui.handlers.canvas.CanvasRectClickedHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Created by v.segouin on 08/09/2016.
 */
public class MatrixVisualizerPane {

    private static final int PADDING_SIZE = 5;
    private static final int SQUARE_SIZE = 25;
    private static final int BASE_OFFSET_Y = 0;
    private static final int BASE_OFFSET_X = 0;
    private static HashMap<Integer, Color> colorCode;

    static {
        colorCode = new HashMap<>();
        colorCode.put(PositionState.GRASS.getStateNumber(), Color.TRANSPARENT);
        colorCode.put(PositionState.MOWED_GRASS.getStateNumber(), Color.GREEN);
        colorCode.put(PositionState.MOWER.getStateNumber(), Color.RED);
    }

    private final MapArea mapArea;

    //Dependency injected by DI
    private MowerController controller;
    private GUIController context;
    //Components
    private Pane overlay;
    private Canvas canvas;
    private Image img;

    /**
     * The basic constructor.
     *
     * @param context         the controller which orchestrate the GUI
     * @param mowerController the controller which controls the mowers
     * @param map             the map that the pane should represents.
     */
    @Inject
    public MatrixVisualizerPane(final GUIController context, final MowerController mowerController, final MapArea map) {
        this.context = context;
        this.controller = mowerController;
        this.canvas = new Canvas();
        this.overlay = new Pane();
        this.img = new Image("/img/arrow.png");
        this.mapArea = map;
    }

    /**
     * This method will reset the pane and draw the current state of the mowers and the map.
     */
    public void drawMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int[][] coordinate = mapArea.getMatrix();
        int offsetX = BASE_OFFSET_X;
        int offsetY = BASE_OFFSET_Y;
        int squareSize = SQUARE_SIZE;
        int padding = PADDING_SIZE;

        canvas.setWidth((squareSize + padding) * coordinate[0].length);
        canvas.setHeight((squareSize + padding) * coordinate.length);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
        overlay.getChildren().removeAll();
        for (int ordinate = mapArea.getMaxOrdinate() - 1; ordinate >= 0; ordinate--) {
            for (int abscissa = 0; abscissa <= mapArea.getMaxAbscissa() - 1; abscissa++) {
                Color color = colorCode.get(coordinate[abscissa][ordinate]);
                Rectangle rectangle = new Rectangle(squareSize, squareSize, color);
                rectangle.setStroke(Color.BLACK);
                rectangle.relocate(offsetX, offsetY);
                rectangle.setOnMouseClicked(new CanvasRectClickedHandler(context, abscissa, ordinate));
                gc.setStroke(Color.BLACK);

                if (coordinate[abscissa][ordinate] == PositionState.MOWER.getStateNumber()) {
                    ImageView iv = loadDirectionArrowImage(rectangle, abscissa, ordinate);
                    overlay.getChildren().addAll(rectangle, iv);
                } else {
                    overlay.getChildren().add(rectangle);
                }
                offsetX += squareSize + padding;
            }
            offsetX = 0;
            offsetY += squareSize + padding;
        }
    }

    /**
     * Return an image view containing an arrow pointing into the mower's directionL.
     *
     * @param rectangle the rectangle where the imageview need to be.
     * @param abscissa  the abscissa of the box.
     * @param ordinate  the ordinate of the box.
     * @return an image view containing an arrow pointing into the mower's directionL.
     */
    private ImageView loadDirectionArrowImage(final Rectangle rectangle, final int abscissa, final int ordinate) {
        ImageView iv = new ImageView(img);
        iv.setLayoutX(rectangle.getLayoutX());
        iv.setLayoutY(rectangle.getLayoutY());
        iv.setFitWidth(rectangle.getWidth());
        iv.setFitHeight(rectangle.getHeight());
        iv.setOnMouseClicked(rectangle.getOnMouseClicked());
        Mower mower = controller.fetchMowerFromCoordinates(context.getCurrentMap(), abscissa, ordinate);
        if (mower != null) {
            iv.setRotate(mower.getDetector().getCurrentState()[2]);
            if (mower.equals(context.getCurrentlySelectedMower())) {
                rectangle.setFill(Color.BLUE);
            }
        }
        return iv;
    }

    public Pane getOverlay() {
        return overlay;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
