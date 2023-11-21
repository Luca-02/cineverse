# Note

- [ ] quando si usa il View Binding, mettere nei Fragment, per buona pratica di coding a detta di 
[android](https://developer.android.com/topic/libraries/view-binding?hl=it), il metodo:
    ```
    @Override
    public void onDestroyView() {
    super.onDestroyView();
    binding = null;
    }
    ```
  
- [ ] per analizzare il progetto con 
[SonarQube](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-for-gradle/#analyzing)
