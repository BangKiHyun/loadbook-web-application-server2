package core.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private VIew vIew;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(VIew vIew) {
        this.vIew = vIew;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public VIew getvIew() {
        return vIew;
    }
}
