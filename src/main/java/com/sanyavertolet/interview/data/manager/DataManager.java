package com.sanyavertolet.interview.data.manager;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.math.CellReference;

import java.util.List;

/**
 * Interface for managing data within a table-like structure. The {@code DataManager} interface provides methods
 * for setting, retrieving, and clearing data, as well as exporting the data and retrieving the dimensions of the table.
 */
public interface DataManager {

    /**
     * Sets the data for a specified cell identified by its row and column indices.
     *
     * @param row   the row index of the cell.
     * @param column the column index of the cell.
     * @param text  the text representation of the data to be set in the cell.
     */
    void setData(int row, int column, String text);

    /**
     * Retrieves the number of rows in the data structure.
     *
     * @return the number of rows.
     */
    int getRowCount();

    /**
     * Retrieves the data stored in a specified cell identified by its row and column indices.
     *
     * @param row    the row index of the cell.
     * @param column the column index of the cell.
     * @return the {@link Data} stored in the specified cell.
     */
    Data getData(int row, int column);

    /**
     * Retrieves the number of columns in the data structure.
     *
     * @return the number of columns.
     */
    int getColumnCount();

    /**
     * Exports the data stored in the structure as a list of {@link CellReference.WithText} objects,
     * each containing the reference and the associated text.
     *
     * @return a list of {@link CellReference.WithText} representing the exported data.
     */
    List<CellReference.WithText> exportData();

    /**
     * Clears all data from the structure, effectively resetting it.
     */
    void clearData();
}
