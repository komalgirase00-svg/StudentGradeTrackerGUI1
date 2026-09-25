import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentGradeTracker extends JFrame {

    // =========================
    // Student Model
    // =========================
    static class Student {

        private final String id;
        private String name;
        private double score;

        public Student(
                String id,
                String name,
                double score
        ) {
            this.id = id;
            this.name = name;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getScore() {
            return score;
        }

        public String getGrade() {

            if (score >= 90) return "A+";
            if (score >= 80) return "A";
            if (score >= 70) return "B";
            if (score >= 60) return "C";
            if (score >= 50) return "D";

            return "F";
        }

        public String getStatus() {
            return score >= 40 ? "PASS" : "FAIL";
        }
    }

    // =========================
    // UI Components
    // =========================
    private JTextField nameField;
    private JTextField scoreField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel totalLabel;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    private JLabel passLabel;
    private JLabel failLabel;

    private String selectedStudentId = null;

    // =========================
    // Colors
    // =========================
    private final Color DARK_BLUE =
            new Color(15, 23, 42);

    private final Color BLUE =
            new Color(37, 99, 235);

    private final Color GREEN =
            new Color(22, 163, 74);

    private final Color RED =
            new Color(220, 38, 38);

    private final Color ORANGE =
            new Color(245, 158, 11);

    private final Color PURPLE =
            new Color(124, 58, 237);

    private final Color LIGHT =
            new Color(248, 250, 252);

    // =========================
    // Constructor
    // =========================
    public StudentGradeTracker() {

        setTitle(
                "Student Grade Management System"
        );

        setSize(1150, 720);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        // Load data when application starts
        loadStudentsFromDatabase();
    }

    // =========================
    // Create UI
    // =========================
    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        mainPanel.setBackground(LIGHT);

        mainPanel.setBorder(
                new EmptyBorder(
                        20, 20, 20, 20
                )
        );

        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        centerPanel.setOpaque(false);

        centerPanel.add(
                createInputPanel(),
                BorderLayout.NORTH
        );

        centerPanel.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                createStatisticsPanel(),
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    // =========================
    // Header
    // =========================
    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(DARK_BLUE);

        header.setBorder(
                new EmptyBorder(
                        18, 25, 18, 25
                )
        );

        JLabel title =
                new JLabel(
                        "STUDENT GRADE MANAGEMENT SYSTEM"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Academic Performance Dashboard"
                );

        subtitle.setForeground(
                new Color(203, 213, 225)
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.add(title);

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(subtitle);

        header.add(
                textPanel,
                BorderLayout.WEST
        );

        return header;
    }

    // =========================
    // Input Panel
    // =========================
    private JPanel createInputPanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 7, 5, 7);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(
                new JLabel("Student Name"),
                gbc
        );

        nameField =
                new JTextField();

        nameField.setPreferredSize(
                new Dimension(180, 35)
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(
                nameField,
                gbc
        );

        // Score
        gbc.gridx = 2;
        gbc.weightx = 0;

        panel.add(
                new JLabel("Score"),
                gbc
        );

        scoreField =
                new JTextField();

        scoreField.setPreferredSize(
                new Dimension(100, 35)
        );

        gbc.gridx = 3;

        panel.add(
                scoreField,
                gbc
        );

        // Add
        JButton addButton =
                createButton(
                        "Add Student",
                        BLUE
                );

        gbc.gridx = 4;

        panel.add(
                addButton,
                gbc
        );

        // Update
        JButton updateButton =
                createButton(
                        "Update",
                        ORANGE
                );

        gbc.gridx = 5;

        panel.add(
                updateButton,
                gbc
        );

        // Delete
        JButton deleteButton =
                createButton(
                        "Delete",
                        RED
                );

        gbc.gridx = 6;

        panel.add(
                deleteButton,
                gbc
        );

        // Clear
        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(100, 116, 139)
                );

        gbc.gridx = 7;

        panel.add(
                clearButton,
                gbc
        );

        addButton.addActionListener(
                e -> addStudent()
        );

        updateButton.addActionListener(
                e -> updateStudent()
        );

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        return panel;
    }

    // =========================
    // Table Panel
    // =========================
    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setOpaque(false);

        // Search
        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(10, 0)
                );

        searchPanel.setOpaque(false);

        JLabel searchLabel =
                new JLabel(
                        "Search Student:"
                );

        searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(300, 35)
        );

        JButton sortButton =
                createButton(
                        "Sort by Score",
                        PURPLE
                );

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                sortButton,
                BorderLayout.EAST
        );

        panel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // Table Model
        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Student ID",
                                "Student Name",
                                "Score",
                                "Grade",
                                "Status"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table =
                new JTable(tableModel);

        table.setRowHeight(35);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        table.getTableHeader().setBackground(
                DARK_BLUE
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.setSelectionBackground(
                new Color(219, 234, 254)
        );

        table.setSelectionForeground(
                Color.BLACK
        );

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(center);

        JScrollPane scrollPane =
                new JScrollPane(table);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Search listener
        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }
                        }
                );

        sortButton.addActionListener(
                e -> sortStudents()
        );

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> selectStudent()
                );

        return panel;
    }

    // =========================
    // Statistics Panel
    // =========================
    private JPanel createStatisticsPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1, 6, 10, 0
                        )
                );

        panel.setOpaque(false);

        totalLabel =
                createStatLabel("0");

        averageLabel =
                createStatLabel("0.00");

        highestLabel =
                createStatLabel("0.00");

        lowestLabel =
                createStatLabel("0.00");

        passLabel =
                createStatLabel("0");

        failLabel =
                createStatLabel("0");

        panel.add(
                createCard(
                        "TOTAL STUDENTS",
                        totalLabel,
                        BLUE
                )
        );

        panel.add(
                createCard(
                        "AVERAGE",
                        averageLabel,
                        PURPLE
                )
        );

        panel.add(
                createCard(
                        "HIGHEST",
                        highestLabel,
                        GREEN
                )
        );

        panel.add(
                createCard(
                        "LOWEST",
                        lowestLabel,
                        RED
                )
        );

        panel.add(
                createCard(
                        "PASSED",
                        passLabel,
                        GREEN
                )
        );

        panel.add(
                createCard(
                        "FAILED",
                        failLabel,
                        RED
                )
        );

        return panel;
    }

    private JPanel createCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(
                                8, 8, 8, 8
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(
                new Color(100, 116, 139)
        );

        value.setForeground(color);

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        return card;
    }

    private JLabel createStatLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        return label;
    }

    // =========================
    // Button
    // =========================
    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(120, 35)
        );

        return button;
    }

    // =========================================================
    // STARTUP LOAD
    // =========================================================
    private void loadStudentsFromDatabase() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT student_id, name, score " +
                        "FROM students ORDER BY id";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student =
                        new Student(
                                rs.getString("student_id"),
                                rs.getString("name"),
                                rs.getDouble("score")
                        );

                addStudentToTable(student);
            }

            updateStatistics();

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // ADD
    // =========================================================
    private void addStudent() {

        String name =
                nameField.getText().trim();

        String scoreText =
                scoreField.getText().trim();

        if (!validateInput(
                name,
                scoreText
        )) {
            return;
        }

        double score =
                Double.parseDouble(scoreText);

        String studentId =
                generateStudentId();

        if (studentId == null) {
            return;
        }

        String sql =
                "INSERT INTO students " +
                        "(student_id, name, score) " +
                        "VALUES (?, ?, ?)";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, studentId);
            ps.setString(2, name);
            ps.setDouble(3, score);

            ps.executeUpdate();

            loadStudentsFromDatabase();

            clearForm();

            showMessage(
                    "Student added successfully!"
            );

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // GENERATE ID
    // =========================================================
    private String generateStudentId() {

        String sql =
                "SELECT MAX(id) AS max_id " +
                        "FROM students";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            int nextNumber = 1;

            if (rs.next()) {

                int maxId =
                        rs.getInt("max_id");

                if (!rs.wasNull()) {
                    nextNumber = maxId + 1;
                }
            }

            return String.format(
                    "ST%03d",
                    nextNumber
            );

        } catch (SQLException e) {

            showDatabaseError(e);

            return null;
        }
    }

    // =========================================================
    // UPDATE
    // =========================================================
    private void updateStudent() {

        if (selectedStudentId == null) {

            showError(
                    "Please select a student first."
            );

            return;
        }

        String name =
                nameField.getText().trim();

        String scoreText =
                scoreField.getText().trim();

        if (!validateInput(
                name,
                scoreText
        )) {
            return;
        }

        double score =
                Double.parseDouble(scoreText);

        String sql =
                "UPDATE students " +
                        "SET name = ?, score = ? " +
                        "WHERE student_id = ?";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name);
            ps.setDouble(2, score);
            ps.setString(3, selectedStudentId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                loadStudentsFromDatabase();

                clearForm();

                showMessage(
                        "Student updated successfully!"
                );

            } else {

                showError(
                        "Student not found."
                );
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // DELETE
    // =========================================================
    private void deleteStudent() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            showError(
                    "Please select a student to delete."
            );

            return;
        }

        String id =
                tableModel
                        .getValueAt(row, 0)
                        .toString();

        String name =
                tableModel
                        .getValueAt(row, 1)
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete " + name + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice !=
                JOptionPane.YES_OPTION) {

            return;
        }

        String sql =
                "DELETE FROM students " +
                        "WHERE student_id = ?";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, id);

            ps.executeUpdate();

            loadStudentsFromDatabase();

            clearForm();

            showMessage(
                    "Student deleted successfully!"
            );

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // SEARCH
    // =========================================================
    private void searchStudents() {

        String keyword =
                searchField
                        .getText()
                        .trim();

        tableModel.setRowCount(0);

        String sql =
                "SELECT student_id, name, score " +
                        "FROM students " +
                        "WHERE student_id LIKE ? " +
                        "OR name LIKE ? " +
                        "ORDER BY id";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    Student student =
                            new Student(
                                    rs.getString(
                                            "student_id"
                                    ),
                                    rs.getString(
                                            "name"
                                    ),
                                    rs.getDouble(
                                            "score"
                                    )
                            );

                    addStudentToTable(student);
                }
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // SORT
    // =========================================================
    private void sortStudents() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT student_id, name, score " +
                        "FROM students " +
                        "ORDER BY score DESC";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student =
                        new Student(
                                rs.getString(
                                        "student_id"
                                ),
                                rs.getString(
                                        "name"
                                ),
                                rs.getDouble(
                                        "score"
                                )
                        );

                addStudentToTable(student);
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // SELECT
    // =========================================================
    private void selectStudent() {

        int row =
                table.getSelectedRow();

        if (row == -1) {
            return;
        }

        selectedStudentId =
                tableModel
                        .getValueAt(row, 0)
                        .toString();

        String name =
                tableModel
                        .getValueAt(row, 1)
                        .toString();

        String score =
                tableModel
                        .getValueAt(row, 2)
                        .toString();

        nameField.setText(name);

        scoreField.setText(score);
    }

    // =========================================================
    // ADD ROW
    // =========================================================
    private void addStudentToTable(
            Student student
    ) {

        tableModel.addRow(
                new Object[]{
                        student.getId(),
                        student.getName(),
                        String.format(
                                "%.2f",
                                student.getScore()
                        ),
                        student.getGrade(),
                        student.getStatus()
                }
        );
    }

    // =========================================================
    // STATISTICS
    // =========================================================
    private void updateStatistics() {

        String sql =
                "SELECT " +
                        "COUNT(*) AS total, " +
                        "COALESCE(AVG(score), 0) AS average, " +
                        "COALESCE(MAX(score), 0) AS highest, " +
                        "COALESCE(MIN(score), 0) AS lowest, " +
                        "COALESCE(SUM(CASE WHEN score >= 40 " +
                        "THEN 1 ELSE 0 END), 0) AS passed, " +
                        "COALESCE(SUM(CASE WHEN score < 40 " +
                        "THEN 1 ELSE 0 END), 0) AS failed " +
                        "FROM students";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                totalLabel.setText(
                        String.valueOf(
                                rs.getInt("total")
                        )
                );

                averageLabel.setText(
                        String.format(
                                "%.2f",
                                rs.getDouble("average")
                        )
                );

                highestLabel.setText(
                        String.format(
                                "%.2f",
                                rs.getDouble("highest")
                        )
                );

                lowestLabel.setText(
                        String.format(
                                "%.2f",
                                rs.getDouble("lowest")
                        )
                );

                passLabel.setText(
                        String.valueOf(
                                rs.getInt("passed")
                        )
                );

                failLabel.setText(
                        String.valueOf(
                                rs.getInt("failed")
                        )
                );
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }

    // =========================================================
    // VALIDATION
    // =========================================================
    private boolean validateInput(
            String name,
            String scoreText
    ) {

        if (name.isEmpty()) {

            showError(
                    "Student name is required."
            );

            return false;
        }

        if (name.length() < 2) {

            showError(
                    "Name must contain at least 2 characters."
            );

            return false;
        }

        if (!name.matches(
                "[a-zA-Z .'-]+"
        )) {

            showError(
                    "Name contains invalid characters."
            );

            return false;
        }

        if (scoreText.isEmpty()) {

            showError(
                    "Score is required."
            );

            return false;
        }

        try {

            double score =
                    Double.parseDouble(
                            scoreText
                    );

            if (score < 0 ||
                    score > 100) {

                showError(
                        "Score must be between 0 and 100."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid numeric score."
            );

            return false;
        }

        return true;
    }

    // =========================================================
    // CLEAR
    // =========================================================
    private void clearForm() {

        nameField.setText("");

        scoreField.setText("");

        selectedStudentId = null;

        table.clearSelection();

        nameField.requestFocus();
    }

    // =========================================================
    // ERROR
    // =========================================================
    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =========================================================
    // SUCCESS
    // =========================================================
    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // DATABASE ERROR
    // =========================================================
    private void showDatabaseError(
            SQLException e
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Database Error:\n" +
                        e.getMessage(),
                "MySQL Error",
                JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
    }

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception e) {

                e.printStackTrace();
            }

            StudentGradeTracker app =
                    new StudentGradeTracker();

            app.setVisible(true);
        });
    }
}
