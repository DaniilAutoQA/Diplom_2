package site.nomoreparties.stellarburgers;

import java.util.List;

public class GetIngredients {

    private boolean success;
    private List<IngredientsJson> data;

    public GetIngredients() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IngredientsJson> getData() {
        return data;
    }

    public void setData(List<IngredientsJson> data) {
        this.data = data;
    }
}
