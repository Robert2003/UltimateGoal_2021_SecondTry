package org.firstinspires.ftc.teamcode.AutonCases;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name="Case0", group="Cases")
public class Case0 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //drive.setPoseEstimate(new Pose2d(-60, -22.5, 0));

        waitForStart();

        if (isStopRequested()) return;

        Trajectory shootingPosition = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(58, 3), 6.127)
                .build();
        Trajectory firstWobble = drive.trajectoryBuilder(shootingPosition.end())
                .splineTo(new Vector2d(74, -20), 4.712)
                .build();
        Trajectory grabSecondWobble = drive.trajectoryBuilder(firstWobble.end(), true)
                .lineToLinearHeading(new Pose2d(30, -20, 3.14))
                .build();
        Trajectory dropSecondWobble = drive.trajectoryBuilder(grabSecondWobble.end())
                .lineToLinearHeading(new Pose2d(63, -20, 4.712))
                .build();
        Trajectory park = drive.trajectoryBuilder(dropSecondWobble.end())
                .lineToLinearHeading(new Pose2d(72, -10, 4.712))
                .build();

        drive.followTrajectory(shootingPosition);
        drive.followTrajectory(firstWobble);
        drive.followTrajectory(grabSecondWobble);
        drive.followTrajectory(dropSecondWobble);
        drive.followTrajectory(park);

        telemetry.addData("heading", drive.getPoseEstimate().getHeading());
        telemetry.update();
        while(opModeIsActive());
    }
}
