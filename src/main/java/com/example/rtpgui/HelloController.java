package com.example.rtpgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.example.rtpgui.MaxFlowDinic.*;

public class HelloController {
    // Входное текстовое поле
    @FXML
    public TextArea inputText;
    // Выходное текстовое поле
    @FXML
    public TextArea outputText;
    // первый радиобатон
    @FXML
    public RadioButton rb1;
    // второй радиобатон
    @FXML
    public RadioButton rb2;
    // третий радиобатон
    @FXML
    public RadioButton rb3;
    @FXML
    public ToggleGroup main;


    // Событие, вызываемое при нажатии на кнопку "Решение"
    public void onResultButtonClick(ActionEvent actionEvent) {
        String bigString = inputText.getText();
        if (bigString.length() != 0) {
            int[][] graphResult = getGraphFromTextArea();
            int len = graphResult.length;
            RadioButton selection = (RadioButton) main.getSelectedToggle();
            switch (selection.getId()) {
                case "rb1":
                    try {
                        FordFulkerson ff = new FordFulkerson();
                        int result = ff.fordFulkerson(graphResult, 0, len - 1);
                        outputText.setText("Max flow: " + result);
                    } catch (Throwable t) {
                        messageError(t.getMessage());
                    }
                    break;
                case "rb2":
                    try {
                        MaxFlowPreflowN3 flow = new MaxFlowPreflowN3();
                        flow.init(len);
                        for (int i = 0; i < len; i++)
                            for (int j = 0; j < len; j++)
                                if (graphResult[i][j] != 0)
                                    flow.addEdge(i, j, graphResult[i][j]);
                        outputText.setText(flow.maxFlow(0, 2) + "");
                    } catch (Throwable t) {
                        messageError(t.getMessage());
                    }
                    break;
                case "rb3":
                    try {
                        if (len == 3) {
                            List<MaxFlowDinic.Edge>[] graph = createGraph(len);
                            addEdge(graph, graphResult[0][0], graphResult[0][1], graphResult[0][2]);
                            addEdge(graph, graphResult[1][0], graphResult[1][1], graphResult[1][2]);
                            addEdge(graph, graphResult[2][0], graphResult[2][1], graphResult[2][2]);
                            outputText.setText(maxFlow(graph, 0, len - 1) + "");
                        } else {
                            messageError("Algorithm for working with a 3x3 matrix.");
                        }
                    } catch (Throwable t) {
                        messageError(t.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // Вывод ошибки
    private static void messageError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    // парсит матрицу из inputText
    public int[][] getGraphFromTextArea() {
        int N;
        int[][] graph;
        Scanner scan = new Scanner(inputText.getText());
        N = scan.nextInt();
        graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                graph[i][j] = scan.nextInt();
            }
        }
        return graph;
    }
}