package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.script.*;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

public class Controller {

    @FXML
    private TextField afield = new TextField();
    @FXML
    private TextField bfield = new TextField();
    @FXML
    private TextField wfield = new TextField();
    @FXML
    private TextArea RhinoTextArea;
    @FXML
    private TextArea JRubyTextArea;
    @FXML
    private TextArea output;
    @FXML
    private Tab RhinoTab;
    @FXML
    private Tab JRubyTab;

    ScriptEngineManager manager;
    ScriptEngine rhinoEngine;
    ScriptEngine jrubyEngine;

    public void PlusBtn_Clicked()
    {
        Integer wynik = Integer.parseInt(afield.getText()) + Integer.parseInt(bfield.getText());
        wfield.setText(wynik.toString());
    }

    public void MinusBtn_Clicked()
    {
        Integer wynik = Integer.parseInt(afield.getText()) - Integer.parseInt(bfield.getText());
        wfield.setText(wynik.toString());
    }

    public void MultiBtn_Clicked()
    {
        Integer wynik = Integer.parseInt(afield.getText()) * Integer.parseInt(bfield.getText());
        wfield.setText(wynik.toString());
    }

    public void DivBtn_Clicked()
    {
        Integer wynik = Integer.parseInt(afield.getText()) / Integer.parseInt(bfield.getText());
        wfield.setText(wynik.toString());
    }

    public void CBtn_Clicked()
    {
        afield.setText("0");
        bfield.setText("0");
        wfield.setText("0");
    }

    public void ExecuteBtn_Clicked()
    {
        output.setText("");
        if(RhinoTab.isSelected())
        {
            try{
                Bindings bindings = rhinoEngine.getBindings(ScriptContext.ENGINE_SCOPE);
                bindings.put("a",Integer.parseInt(afield.getText()));
                bindings.put("b",Integer.parseInt(bfield.getText()));
                bindings.put("result",wfield);
                rhinoEngine.eval(RhinoTextArea.getText(),bindings);
                output.setText("Script executed");
            }catch (ScriptException e)
            {
                output.setText(e.toString());
            }
        }
        if(JRubyTab.isSelected())
        {
            try{
                Bindings bindings = jrubyEngine.getBindings(ScriptContext.ENGINE_SCOPE);
                bindings.put("a",Integer.parseInt(afield.getText()));
                bindings.put("b",Integer.parseInt(bfield.getText()));
                bindings.put("result",wfield);
                jrubyEngine.eval(JRubyTextArea.getText(),bindings);
                output.setText("Script executed");
            }catch (ScriptException e)
            {
                output.setText(e.toString());
            }
        }
    }

    public void ClearBtn_Clicked()
    {
        RhinoTextArea.setText("");
        JRubyTextArea.setText("");
        output.setText("");
    }

    @FXML
    private void initialize()
    {
        afield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    afield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        bfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    bfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        manager = new ScriptEngineManager();
        rhinoEngine = manager.getEngineByName("rhino");
        jrubyEngine = manager.getEngineByName("jruby");
    }


}
