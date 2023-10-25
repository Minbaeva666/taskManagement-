package com.example.taskmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import java.util.Date;

public class HelloController {
    @FXML
    private RadioButton homeworkRadio;

    @FXML
    private RadioButton meetingRadio;

    @FXML
    private RadioButton shoppingRadio;

    @FXML
    private TextField taskNameField;

    @FXML
    private TextField taskDescriptionField;

    @FXML
    private ChoiceBox<Priority> priorityChoiceBox;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private ListView<Task> listView;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        priorityChoiceBox.getItems().setAll(Priority.values());
        listView.setItems(tasks);
    }

    @FXML
    private void addTask() {
        // Get user inputs
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        Priority priority = priorityChoiceBox.getValue();
        LocalDate localDeadline = deadlinePicker.getValue();
        Date deadline = Date.from(localDeadline.atStartOfDay(ZoneId.systemDefault()).toInstant());


        Task newTask = null;
        if (homeworkRadio.isSelected()) {
            newTask = new HomeworkTask();
        } else if (meetingRadio.isSelected()) {
            newTask = new MeetingTask();
        } else if (shoppingRadio.isSelected()) {
            newTask = new ShoppingTask();
        }

        if (newTask != null) {
            newTask.setTask(taskName, taskDescription);
            newTask.setPriority(priority);
            newTask.setDeadline(deadline);
            tasks.add(newTask);
        }

        taskNameField.clear();
        taskDescriptionField.clear();
        priorityChoiceBox.setValue(null);
        deadlinePicker.setValue(null);
    }

    @FXML
    private void markAsComplete() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = listView.getItems().get(selectedIndex);
            selectedTask.markAsComplete();
            listView.refresh(); // Refresh the ListView to reflect the updated task status
        }
    }
}
