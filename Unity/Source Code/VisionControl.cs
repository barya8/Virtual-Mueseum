using UnityEngine;
using UnityEngine.Rendering;


[RequireComponent(typeof(Volume))]
public class VisionControl : MonoBehaviour
{
    [SerializeField] private Color defaultLightColour;
    [SerializeField] private Color boostedLightColour;

    private bool isNightVisionEnabled;
    private bool isThermalEnabled;

    private Volume nvgVolume;
    public GameObject thermalCamera;
    public GameObject EnviromentPP;
    public GameObject ThermalPP;

    private void Start()
    {
        RenderSettings.ambientLight = defaultLightColour;

        nvgVolume = gameObject.GetComponent<Volume>();
        nvgVolume.weight = 0;
        //thermalCamera = GameObject.FindGameObjectWithTag("Thermal");
    }

    private void Update()
    {
       
    }

    public void ToggleNightVision()
    {
        if (isThermalEnabled)
            ToggleThermalVision();

        isNightVisionEnabled = !isNightVisionEnabled;

        if (isNightVisionEnabled)
        {
            RenderSettings.ambientLight = boostedLightColour;
            nvgVolume.weight = 1;
        }
        else
        {
            RenderSettings.ambientLight = defaultLightColour;
            nvgVolume.weight = 0;
        }
    }

    public void ToggleThermalVision()
    {
        if(isNightVisionEnabled)
            ToggleNightVision();

        isThermalEnabled = !isThermalEnabled;
        if (isThermalEnabled)
        {
            thermalCamera.SetActive(true);
            EnviromentPP.SetActive(true);
            ThermalPP.SetActive(true);
        }
        else
        {
            thermalCamera.SetActive(false);
            EnviromentPP.SetActive(false);
            ThermalPP.SetActive(false);

        }
    }
}