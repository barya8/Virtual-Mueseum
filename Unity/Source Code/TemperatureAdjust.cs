using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.XR.Content.Interaction;

public class TemperatureAdjust : MonoBehaviour
{
    public XRKnob Wheel;
    public GameObject ThermalCube;
    public GameObject ThermalCamera;
    [SerializeField]
    private Material ThermalMaterial;

    private float temperature;
    private Renderer rend;
    // Start is called before the first frame update
    void Start()
    {
        rend = GetComponent<Renderer>();
        ThermalMaterial = rend.sharedMaterial;
        ThermalMaterial.SetFloat("_Temperature", 0f);
        this.gameObject.layer = 8;
        Wheel.value = 0.01f;
    }

    // Update is called once per frame
    void Update()
    {
        if(ThermalCamera.activeInHierarchy)
        {
            temperature = Mathf.Lerp(0f, 150f, Wheel.value );
            print(ThermalMaterial.GetFloat("_Temperature"));
            ThermalMaterial.SetFloat("_Temperature", temperature);
        }
        else
            ThermalMaterial.SetFloat("_Temperature", 0f);

    }
}
