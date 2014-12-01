<?php

include_once('./push/ios/push_score.php');

$regid = "65adb2c79ac885c6f780d97e754675d71d934b0e6bea7b35f804120d10fd6938";

$message = "The scores are in! \nYou scored 789 points!";

print_r(push_ios_notification($regid, $message));


?>