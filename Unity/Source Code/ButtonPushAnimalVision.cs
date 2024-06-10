using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.Interaction.Toolkit;

public class ButtonPushAnimalVision : MonoBehaviour
{
    public enum VisionSelectEnum
    {
        NVG = 0,
        THERMAL
    }
    public VisionSelectEnum VisionSelect;
    public VisionControl visionControl;
    public Animator animator;
    public string boolName = "Open";
    // Start is called before the first frame update
    void Start()
    {
        GetComponent<XRSimpleInteractable>().selectEntered.AddListener(x => ToggleButton());
        visionControl = FindObjectOfType<VisionControl>();
    }

    public void ToggleButton()
    {
        //bool isOpen = animator.GetBool(boolName);
        //animator.SetBool(boolName, !isOpen);
        //AudioManager.instance.Play("Door");
        switch(VisionSelect)
        {
            case VisionSelectEnum.NVG:
                visionControl.ToggleNightVision();
                break;
                case VisionSelectEnum.THERMAL:
                visionControl.ToggleThermalVision();
                break;
        }

    }

  
}
